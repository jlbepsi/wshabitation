package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.HabitationException;
import devops.kilroywashere.wshabitation.models.HabitationNotFoundException;
import devops.kilroywashere.wshabitation.models.Typehabitat;
import devops.kilroywashere.wshabitation.repositories.TypehabitatsRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class TypehabitatsServiceImpl implements GenericCrudService<Typehabitat>  {
    private final TypehabitatsRepository repository;

    public TypehabitatsServiceImpl(TypehabitatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Typehabitat> findAll() {
        return repository.findAll();
    }

    @Override
    public Typehabitat findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Typehabitat add(@NotNull Typehabitat typehabitat) {
        return repository.save(typehabitat);
    }

    @Override
    public Typehabitat update(@NotNull Typehabitat typehabitat) throws HabitationException {
        // Recherche du typehabitat
        Typehabitat typehabitatToUpdate = findById(typehabitat.getId());
        if (typehabitatToUpdate == null)
            throw new HabitationNotFoundException();

        typehabitatToUpdate.setLibelle(typehabitat.getLibelle());
        try {
            // Mise à jour dans la BD
            repository.save(typehabitatToUpdate);
            return typehabitatToUpdate;
        } catch (Exception exception) {
            throw new HabitationException(exception.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws HabitationException {
        // Recherche du typehabitat
        Typehabitat typehabitat = findById(id);
        if (typehabitat == null)
            throw new HabitationNotFoundException();


        try {
            // Suppression dans la BD
            repository.delete(typehabitat);
        } catch (Exception exception) {
            throw new HabitationException(exception.getMessage());
        }
    }
}
