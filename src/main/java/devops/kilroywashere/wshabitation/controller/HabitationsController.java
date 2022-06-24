package devops.kilroywashere.wshabitation.controller;


import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.repositories.HabitationsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class HabitationsController {
    private final HabitationsRepository repository;

    // DI par Spring Boot
    public HabitationsController(HabitationsRepository repository) {
        this.repository = repository;
    }

    /**
     * Retourne toutes les habitations
     *
     * @return Une liste d'objet Habitation
     */
    @GetMapping("/habitations")
    public Iterable<Habitation> getHabitations() {
        return repository.findAll();
    }

    /** Retourne l'habitation associée à l'id ou une erreur si non trouvée
     * Méthode REST GET publique
     *
     * @param id Id de l’habitation
     * @return L'objet Habitation correspondant à l'id ou une erreur ResponseStatusException
     */
    @GetMapping("/habitations/{id}")
    public Habitation getHabitations(@PathVariable(value = "id") Long id) {
        return findById(id);
    }

    /** Ajoute une habitation
     *
     * @param habitation Objet Habitation contenant les informations à ajouter
     * @return Le status HTTP 201: Created avec l'url de la ressource créée si tout est ok
     * *       Le status HTTP BAD_REQUEST sinon
     * * @see Habitation
     */
    @PostMapping("/habitations")
    public ResponseEntity<Void> addHabitation(@Validated @RequestBody Habitation habitation) {
        try {
            Habitation habitationCreated = repository.save(habitation);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(habitationCreated.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception exception) {
            LogError(exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Habitation: %s", habitation.toString())
            );
        }
    }

    /** Mise à jour d'un livre
     *
     * @param id Id de l’habitation
     * @param habitation Objet Habitation contenant les informations mises à jour
     * @return Le status HTTP 200 si tout est ok
     *         Le status HTTP NOT_FOUND si l'habitation n'existe pas
     *           ou BAD_REQUEST sinon
     */
    @PutMapping("/habitations/{id}")
    public Habitation updateHabitation(@PathVariable Long id, @Validated @RequestBody Habitation habitation) {
        // Vérification des paramètres
        if (! id.equals(habitation.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "L'id de l'URL ne correspond pas à l'id du body"
            );
        }

        // Recherche de l'habitation
        Habitation habitationUpdated = findById(id);
        if (habitationUpdated == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", id)
            );
        }

        // Mise à jour des données de l'objet
        habitationUpdated.updateFrom(habitation);
        try {
            // Mise à jour dans la BD
            repository.save(habitationUpdated);
            // HTTP Status Code 200 (Ok) par défaut
            LogMessage(String.format("Habitation mis à jour: %s", habitationUpdated.toString()));

            return habitationUpdated;
        } catch (Exception exception) {
            LogError(exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Habitation: %s", habitationUpdated.toString())
            );
        }
    }

    /** Supprime une habitation
     *
     * @param id L'id de l'habitation à supprimer
     * @return HTTP Status Code 204 NO CONTENT(succès), Code 404 (ressource non trouvée)
     *              ou Code 500 Erreur serveur
     */
    @DeleteMapping("/habitations/{id}")
    public ResponseEntity<Void> deleteHabitation(@PathVariable Long id) {
        boolean success = false;

        // Recherche de l'habitation
        Habitation habitation = findById(id);
        if (habitation == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", id)
            );
        }

        try {
            // Mise à jour dans la BD
            repository.delete(habitation);
            // HTTP Status Code 204 (NO CONTENT)
            LogMessage(String.format("Habitation supprimée: %s", habitation.toString()));

            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            LogError(exception);

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Habitation: %s", habitation.toString())
            );
        }
    }



    /** Retourne l'habitation associée à l'id ou une erreur si non trouvée
     * Méthode interne privée
     *
     * @param id Numéro de l’habitation
     * @return L'objet Habitation correspondant à l'id ou une erreur ResponseStatusException
     */
    private Habitation findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Habitation '%s' non trouvée", id))
                );
    }

    /**
     * Loggue les exceptions
     * TODO : mettre en oeuvre Log4J
     *
     * @param exception Exception lancée
     */
    private void LogError(Exception exception) {
        System.err.printf("Error, Class=%s\n", this.getClass().getCanonicalName());
        exception.printStackTrace(System.err);
    }

    /**
     * Loggue les messages
     * TODO : mettre en oeuvre Log4J
     *
     * @param message Message envoyé
     */
    private void LogMessage(String message) {
        System.err.printf("Message=%s, Class=%s\n", message, this.getClass().getCanonicalName());
    }

}
