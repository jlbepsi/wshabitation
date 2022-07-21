package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.*;
import devops.kilroywashere.wshabitation.repositories.HabitationOptionsRepository;
import devops.kilroywashere.wshabitation.repositories.OptionsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HabitationOptionsServiceImpl implements HabitationOptionService {
    private final HabitationOptionsRepository habitationOptionsRepository;
    private final OptionsRepository itemsRepository;

    // DI par Spring Boot
    public HabitationOptionsServiceImpl(
            HabitationOptionsRepository habitationOptionsRepository,
            OptionsRepository itemsRepository
    ) {
        this.habitationOptionsRepository = habitationOptionsRepository;
        this.itemsRepository = itemsRepository;
    }

    /**
     * Retourne toutes les habitations
     *
     * @return Une liste d'objet Habitation
     */
    @Override
    public Iterable<HabitationOptionpayante> findAllHabitationOptionsPayantes() {
        return habitationOptionsRepository.findAll();
    }

    @Override
    public Iterable<HabitationOptionpayante> findAllHabitationOptionsPayantesByHabitation(int id) {
        return habitationOptionsRepository.findHabitationOptionpayantesByHabitation_Id(id);
    }

    @Override
    public void addAllHabitationOptionIds(Habitation habitation, List<HabitationOptionsDTO.HabitationOptionPrixDTO> optionsIdPrix) {
        // Liste des items et prix
        List<HabitationOptionpayante> habitationOptions = new ArrayList<>();
        for (HabitationOptionsDTO.HabitationOptionPrixDTO idPrix : optionsIdPrix) {
            // Recherche de l'option
            Optional<Optionpayante> optionpayante = itemsRepository.findById(idPrix.getOptionId());
            if (optionpayante.isPresent()) {
                HabitationOptionpayanteId optionId = new HabitationOptionpayanteId();
                optionId.setHabitationId(habitation.getId());
                optionId.setOptionpayanteId(idPrix.getOptionId());

                HabitationOptionpayante habitationOptionpayante = new HabitationOptionpayante();
                habitationOptionpayante.setId(optionId);
                habitationOptionpayante.setHabitation(habitation);
                habitationOptionpayante.setOptionpayante(optionpayante.get());
                habitationOptionpayante.setPrix(idPrix.getPrix());
                habitationOptions.add(habitationOptionpayante);
            }
        }

        habitationOptionsRepository.saveAll(habitationOptions);

    }
}
