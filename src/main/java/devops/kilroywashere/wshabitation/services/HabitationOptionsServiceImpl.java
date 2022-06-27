package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.*;
import devops.kilroywashere.wshabitation.repositories.HabitationOptionsRepository;
import devops.kilroywashere.wshabitation.repositories.OptionsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void addAllHabitationOptionIds(Habitation habitation, List<Integer> optionsIds) {
        // Recherche des items
        List<Optionpayante> optionpayantes = (List<Optionpayante>) itemsRepository.findAllById(optionsIds);

        List<HabitationOptionpayante> habitationOptions = new ArrayList<>();
        for (int id : optionsIds) {
            HabitationOptionpayanteId optionId = new HabitationOptionpayanteId();
            optionId.setHabitationId(habitation.getId());
            optionId.setOptionpayanteId(id);

            HabitationOptionpayante habitationOptionpayante = new HabitationOptionpayante();
            habitationOptionpayante.setId(optionId);
            habitationOptionpayante.setHabitation(habitation);
            habitationOptionpayante.setOptionpayante(
                    optionpayantes.stream()
                            .filter(i -> i.getId() == id)
                            .findFirst()
                            .orElse(null)
            );
            habitationOptions.add(habitationOptionpayante);
        }

        habitationOptionsRepository.saveAll(habitationOptions);

    }
}
