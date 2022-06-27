package devops.kilroywashere.wshabitation.controller;

import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.HabitationOptionsDTO;
import devops.kilroywashere.wshabitation.services.HabitationOptionService;
import devops.kilroywashere.wshabitation.services.HabitationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class HabitationOptionpayantesController {
    private final HabitationsService habitationsService;
    private final HabitationOptionService habitationOptionService;

    /**
     * Logging
     */
    Logger logger = LoggerFactory.getLogger(HabitationOptionpayantesController.class);

    // DI par Spring Boot
    public HabitationOptionpayantesController(
            HabitationsService habitationsService,
            HabitationOptionService habitationOptionService
    ) {
        this.habitationsService = habitationsService;
        this.habitationOptionService = habitationOptionService;
    }

    @PostMapping("/habitationitems")
    public ResponseEntity<Void> addHabitationOptions(@Validated @RequestBody HabitationOptionsDTO optionsDTO) {
        Habitation habitation = null;
        try {
            // Recherche de l'habitation
            habitation = habitationsService.findHabitationById(optionsDTO.getHabitationId());
        } catch (Exception ignored) {}
        if (habitation == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("L'habitation ayant l'id (%d) n'existe pas", optionsDTO.getHabitationId())
            );
        }

        try {
            habitationOptionService.addAllHabitationOptionIds(habitation, optionsDTO.getOptionIds());

            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            logger.error("HabitationOptionpayantesController", exception);

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Habitation OptionPayantes non conforme"
            );
        }

    }
}
