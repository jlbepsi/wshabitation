package devops.kilroywashere.wshabitation.controller;

import devops.kilroywashere.wshabitation.models.*;
import devops.kilroywashere.wshabitation.services.HabitationItemsService;
import devops.kilroywashere.wshabitation.services.HabitationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class HabitationItemsController {
    private final HabitationsService habitationsService;
    private final HabitationItemsService habitationItemsService;

    /**
     * Logging
     */
    Logger logger = LoggerFactory.getLogger(HabitationItemsController.class);

    // DI par Spring Boot
    public HabitationItemsController(
            HabitationsService habitationsService,
            HabitationItemsService habitationItemsService
    ) {
        this.habitationsService = habitationsService;
        this.habitationItemsService = habitationItemsService;
    }

    @PostMapping("/habitationitems")
    public ResponseEntity<Void> addHabitationItems(@Validated @RequestBody HabitationItemsDTO itemsDTO) {
        Habitation habitation = null;
        try {
            // Recherche de l'habitation
            habitation = habitationsService.findHabitationById(itemsDTO.getHabitationId());
        } catch (Exception ignored) {}
        if (habitation == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", itemsDTO.getHabitationId())
            );
        }

        try {
            habitationItemsService.addAllHabitationItemIds(habitation, itemsDTO.getItemIds());

            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            logger.error("HabitationItemsController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Habitation Items non conforme"
            );
        }

    }
}
