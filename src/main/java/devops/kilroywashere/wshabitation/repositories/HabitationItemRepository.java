package devops.kilroywashere.wshabitation.repositories;

import org.springframework.data.repository.CrudRepository;

import devops.kilroywashere.wshabitation.models.HabitationItem;
import devops.kilroywashere.wshabitation.models.HabitationItemId;

import java.util.List;

public interface HabitationItemRepository extends CrudRepository<HabitationItem, HabitationItemId> {
    List<HabitationItem> findHabitationItemsByHabitation_Id(int id);
}
