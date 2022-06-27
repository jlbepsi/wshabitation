package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.*;
import devops.kilroywashere.wshabitation.repositories.HabitationItemRepository;
import devops.kilroywashere.wshabitation.repositories.HabitationOptionsRepository;
import devops.kilroywashere.wshabitation.repositories.HabitationsRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class HabitationsServiceImpl implements HabitationsService {
    private final HabitationsRepository repository;
    private final HabitationItemRepository habitationItemRepository;
    private final HabitationOptionsRepository habitationOptionsRepository;

    // DI par Spring Boot
    public HabitationsServiceImpl(
            HabitationsRepository repository,
            HabitationItemRepository habitationItemRepository,
            HabitationOptionsRepository habitationOptionsRepository
    ) {
        this.repository = repository;
        this.habitationItemRepository = habitationItemRepository;
        this.habitationOptionsRepository = habitationOptionsRepository;
    }

    /**
     * Retourne toutes les habitations
     *
     * @return Une liste d'objet Habitation
     */
    @Override
    public Iterable<Habitation> findAllHabitations() {
        return repository.findAll();
    }

    /** Retourne l'habitation associée à l'id
     *
     * @param id Id de l’habitation
     * @return Un objet Optional contenant l'objet Habitation correspondant à l'id ou
     *         un objet Optional vide
     */
    @Override
    public Habitation findHabitationById(@NotNull Integer id) {
        return repository.findById(id).orElse(null);
    }

    /** Ajout d'une habitation
     *
     * @param habitation Objet Habitation contenant les informations à ajouter
     * @return L'objet Habitation créé
     *
     * @see Habitation
     */
    @Override
    public Habitation addHabitation(@NotNull Habitation habitation) {
        // Les items et les options ne sont pas enregistrés
        habitation.setItems(null);
        habitation.setOptionpayantes(null);

        return repository.save(habitation);
    }

    /** Mise à jour d'une habitation
     *
     * @param habitation Objet Habitation contenant les informations mises à jour
     * @return L'objet Habitation modifié ou null en cas d'erreur
     */
    @Override
    public Habitation updateHabitation(@NotNull Habitation habitation)
        throws HabitationException {
        // Recherche de l'habitation
        Habitation habitationUpdated = findHabitationById(habitation.getId());
        if (habitationUpdated == null) {
            throw new HabitationNotFoundException();
        }

        // Mise à jour des données de l'objet
        habitationUpdated.updateFrom(habitation);
        try {
            // Mise à jour dans la BD
            repository.save(habitationUpdated);
            return habitationUpdated;
        } catch (Exception exception) {
            throw new HabitationException(exception.getMessage());
        }
    }

    /** Suppression d'une habitation associée à l'id
     *
     * @param id Id de l’habitation
     */
    @Override
    public void deleteHabitation(@NotNull Integer id)
            throws HabitationException {
        // Recherche de l'habitation
        Habitation habitation = findHabitationById(id);
        if (habitation == null) {
            throw new HabitationNotFoundException();
        }

        try {
            // Suppression dans la BD
            deleteHabitation(habitation);
        } catch (Exception exception) {
            throw new HabitationException(exception.getMessage());
        }
    }

    private void deleteHabitation(Habitation habitation)
            throws Exception
    {
        // Suppression de toutes les liaisons habitation/item
        List<HabitationItem> items = habitationItemRepository.findHabitationItemsByHabitation_Id(habitation.getId());
        habitationItemRepository.deleteAll(items);

        // Suppression de toutes les liaisons habitation/optionpayante
        List<HabitationOptionpayante> optionpayantes = habitationOptionsRepository.findHabitationOptionpayantesByHabitation_Id(habitation.getId());
        habitationOptionsRepository.deleteAll(optionpayantes);

        // Suppression de l'habitation
        repository.delete(habitation);
    }
}
