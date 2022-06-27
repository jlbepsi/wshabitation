package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.HabitationItem;
import devops.kilroywashere.wshabitation.models.HabitationItemId;
import devops.kilroywashere.wshabitation.models.Item;
import devops.kilroywashere.wshabitation.repositories.HabitationItemRepository;
import devops.kilroywashere.wshabitation.repositories.ItemsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitationItemsServiceImpl implements HabitationItemsService {
    private final HabitationItemRepository habitationItemRepository;
    private final ItemsRepository itemsRepository;

    // DI par Spring Boot
    public HabitationItemsServiceImpl(
            HabitationItemRepository habitationItemRepository,
            ItemsRepository itemsRepository
    ) {
        this.habitationItemRepository = habitationItemRepository;
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void addAllHabitationItemIds(Habitation habitation, List<Integer> itemIds) {
        // Recherche des items
        List<Item> items = (List<Item>) itemsRepository.findAllById(itemIds);

        List<HabitationItem> habitationItems = new ArrayList<>();
        for (int id : itemIds) {
            HabitationItemId itemId = new HabitationItemId();
            itemId.setHabitationId(habitation.getId());
            itemId.setItemId(id);

            HabitationItem habitationItem = new HabitationItem();
            habitationItem.setId(itemId);
            habitationItem.setHabitation(habitation);
            habitationItem.setItem(
                    items.stream()
                            .filter(i -> i.getId() == id)
                            .findFirst()
                            .orElse(null)
            );
            habitationItems.add(habitationItem);
        }

        habitationItemRepository.saveAll(habitationItems);

    }
}
