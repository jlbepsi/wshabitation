package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.Habitation;

import java.util.List;

public interface HabitationOptionService {

    void addAllHabitationOptionIds(Habitation habitation, List<Integer> optionIds);
}
