package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.HabitationOptionpayante;
import devops.kilroywashere.wshabitation.models.HabitationOptionsDTO;

import java.util.List;

public interface HabitationOptionService {
    Iterable<HabitationOptionpayante> findAllHabitationOptionsPayantes();
    Iterable<HabitationOptionpayante> findAllHabitationOptionsPayantesByHabitation(int id);

    void addAllHabitationOptionIds(Habitation habitation, List<HabitationOptionsDTO.HabitationOptionPrixDTO> optionIds);
}
