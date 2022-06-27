package devops.kilroywashere.wshabitation.controller;

import devops.kilroywashere.wshabitation.models.Optionpayante;
import devops.kilroywashere.wshabitation.repositories.OptionsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OptionpayantesController {
    private final OptionsRepository repository;

    public OptionpayantesController(OptionsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/options")
    public Iterable<Optionpayante> getItems() {
        return repository.findAll();
    }
}
