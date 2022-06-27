package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.Habitation;

import java.util.List;

public interface HabitationItemsService {

    void addAllHabitationItemIds(Habitation habitation, List<Integer> itemIds);
}
