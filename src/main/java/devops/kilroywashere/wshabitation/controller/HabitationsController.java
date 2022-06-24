package devops.kilroywashere.wshabitation.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.HabitationException;
import devops.kilroywashere.wshabitation.models.HabitationNotFoundException;
import devops.kilroywashere.wshabitation.services.HabitationsService;


@RestController
@RequestMapping("/api")
public class HabitationsController {
    private final HabitationsService service;

    /**
     * Logging
     */
    Logger logger = LoggerFactory.getLogger(HabitationsController.class);

    // DI par Spring Boot
    public HabitationsController(HabitationsService service) {
        this.service = service;
    }

    /**
     * Retourne toutes les habitations
     *
     * @return Une liste d'objet Habitation
     */
    @GetMapping("/habitations")
    public Iterable<Habitation> getHabitations() {
        logger.info("getHabitations");
        return service.findAllHabitations();
    }

    /** Retourne l'habitation associée à l'id ou une erreur si non trouvée
     * Méthode REST GET publique
     *
     * @param id Id de l’habitation
     * @return L'objet Habitation correspondant à l'id ou une erreur ResponseStatusException
     *         Le status HTTP NOT_FOUND si l'habitation n'existe pas
     */
    @GetMapping("/habitations/{id}")
    public Habitation getHabitations(@PathVariable(value = "id") Integer id) {
        Habitation habitation = service.findHabitationById(id);

        if (habitation == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", id)
            );
        }

        return habitation;
    }

    /** Ajoute une habitation
     *
     * @param habitation Objet Habitation contenant les informations à ajouter
     * @return Le status HTTP 201: Created avec l'url de la ressource créée si tout est ok
     *        Le status HTTP BAD_REQUEST sinon
     * @see Habitation
     */
    @PostMapping("/habitations")
    public ResponseEntity<Void> addHabitation(@Validated @RequestBody Habitation habitation) {
        try {
            Habitation habitationCreated = service.addHabitation(habitation);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(habitationCreated.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception exception) {
            logger.error("HabitationsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Habitation: %s", habitation.toString())
            );
        }
    }

    /** Mise à jour d'une habitation
     *
     * @param id Id de l’habitation
     * @param habitation Objet Habitation contenant les informations mises à jour
     * @return Le status HTTP 200 si tout est ok
     *         Le status HTTP NOT_FOUND si l'habitation n'existe pas
     *           ou BAD_REQUEST sinon
     */
    @PutMapping("/habitations/{id}")
    public Habitation updateHabitation(@PathVariable Integer id, @Validated @RequestBody Habitation habitation) {
        // Vérification des paramètres
        if (! id.equals(habitation.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "L'id de l'URL ne correspond pas à l'id du body"
            );
        }

        Habitation habitationUpdated;
        try {
            habitationUpdated = service.updateHabitation(habitation);
        } catch (HabitationNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", id)
            );
        } catch (HabitationException exception) {
            logger.error("HabitationsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Habitation: %s", habitation)
            );
        }

        // HTTP Status Code 200 (Ok) par défaut
        logger.info(String.format("Habitation mis à jour: %s", habitationUpdated));
        return habitationUpdated;
    }

    /** Supprime une habitation
     *
     * @param id L'id de l'habitation à supprimer
     * @return HTTP Status Code 204 NO CONTENT(succès), Code 404 (ressource non trouvée)
     *              ou Code 500 Erreur serveur
     */
    @DeleteMapping("/habitations/{id}")
    public ResponseEntity<Void> deleteHabitation(@PathVariable Integer id) {
        try {
            service.deleteHabitation(id);
            logger.info(String.format("Habitation ayant l'id (%d)  a été supprimé", id));

            // HTTP Status Code 204 (NO CONTENT)
            return ResponseEntity.noContent().build();
        } catch (HabitationNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", id)
            );
        } catch (HabitationException exception) {
            logger.error("HabitationsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Habitation ayant l'id (%d) n'a pas pu être supprimée", id)
            );
        }
    }
}
