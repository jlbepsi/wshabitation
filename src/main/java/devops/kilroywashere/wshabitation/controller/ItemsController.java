package devops.kilroywashere.wshabitation.controller;

import devops.kilroywashere.wshabitation.models.Item;
import devops.kilroywashere.wshabitation.repositories.ItemsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ItemsController {
    private final ItemsRepository repository;

    public ItemsController(ItemsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/items")
    public Iterable<Item> getItems() {
        return repository.findAll();
    }
}
