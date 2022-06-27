package devops.kilroywashere.wshabitation.repositories;

import devops.kilroywashere.wshabitation.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<Item, Integer> {
}
