package devops.kilroywashere.wshabitation.controller;

import devops.kilroywashere.wshabitation.models.HabitationException;
import devops.kilroywashere.wshabitation.models.HabitationNotFoundException;
import devops.kilroywashere.wshabitation.models.Typehabitat;
import devops.kilroywashere.wshabitation.services.GenericCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1")
public class TypehabitatsController {
    private final GenericCrudService<Typehabitat> service;

    /**
     * Logging
     */
    Logger logger = LoggerFactory.getLogger(TypehabitatsController.class);

    // DI par Spring Boot
    public TypehabitatsController(GenericCrudService<Typehabitat> service) {
        this.service = service;
    }

    /**
     * Retourne toutes les types d'habitat
     *
     * @return Une liste d'objet Typehabitat
     */
    @GetMapping("/typehabitats")
    public Iterable<Typehabitat> getTypehabitats() {
        return service.findAll();
    }

    /** Retourne le type d'habitat associée à l'id ou une erreur si non trouvé
     * Méthode REST GET publique
     *
     * @param id Id du type d'habitat
     * @return L'objet Typehabitat correspondant à l'id ou une erreur ResponseStatusException
     *         Le status HTTP NOT_FOUND si le type d'habitat n'existe pas
     */
    @GetMapping("/typehabitats/{id}")
    public Typehabitat getTypehabitat(@PathVariable(value = "id") Integer id) {
        Typehabitat typehabitat = service.findById(id);

        if (typehabitat == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Le type d'habitat ayant l'id (%d) n'existe pas", id)
            );
        }

        return typehabitat;
    }

    /** Ajoute un type d'habitat
     *
     * @param typehabitat Objet Habitation contenant les informations à ajouter
     * @return Le status HTTP 201: Created avec l'url de la ressource créée si tout est ok
     *        Le status HTTP BAD_REQUEST sinon
     * @see Typehabitat
     */
    @PostMapping("/typehabitats")
    public ResponseEntity<Void> addTypehabitat(@Validated @RequestBody Typehabitat typehabitat) {
        try {
            Typehabitat typehabitatCreated = service.add(typehabitat);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(typehabitatCreated.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception exception) {
            logger.error("TypehabitatsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Typehabitat: %s", typehabitat.toString())
            );
        }
    }

    /** Mise à jour du type d'habitat
     *
     * @param id Id du type d'habitat
     * @param typehabitat Objet Habitation contenant les informations mises à jour
     * @return Le status HTTP 200 si tout est ok
     *         Le status HTTP NOT_FOUND si l'habitation n'existe pas
     *           ou BAD_REQUEST sinon
     */
    @PutMapping("/typehabitats/{id}")
    public Typehabitat updateTypehabitat(@PathVariable Integer id, @Validated @RequestBody Typehabitat typehabitat) {
        // Vérification des paramètres
        if (! id.equals(typehabitat.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "L'id de l'URL ne correspond pas à l'id du body"
            );
        }

        Typehabitat typehabitatUpdated;
        try {
            typehabitatUpdated = service.update(typehabitat);
        } catch (HabitationNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Le type d'habitat ayant l'id (%d) n'existe pas", id)
            );
        } catch (HabitationException exception) {
            logger.error("TypehabitatsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Typehabitat: %s", typehabitat)
            );
        }

        // HTTP Status Code 200 (Ok) par défaut
        logger.info(String.format("Typehabitat mis à jour: %s", typehabitatUpdated));
        return typehabitatUpdated;
    }

    /** Supprime un type d'habitat
     *
     * @param id L'id du type d'habitat à supprimer
     * @return HTTP Status Code 204 NO CONTENT(succès), Code 404 (ressource non trouvée)
     *              ou Code 500 Erreur serveur
     */
    @DeleteMapping("/typehabitats/{id}")
    public ResponseEntity<Void> deleteTypehabitat(@PathVariable Integer id) {
        try {
            service.delete(id);
            logger.info(String.format("Le type d'habitat ayant l'id (%d)  a été supprimé", id));

            // HTTP Status Code 204 (NO CONTENT)
            return ResponseEntity.noContent().build();
        } catch (HabitationNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Le type d'habitat ayant l'id (%d) n'existe pas", id)
            );
        } catch (HabitationException exception) {
            logger.error("TypehabitatsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Typehabitat ayant l'id (%d) n'a pas pu être supprimée", id)
            );
        }
    }
}
