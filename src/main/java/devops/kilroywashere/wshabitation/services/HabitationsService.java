package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.HabitationException;

import javax.validation.constraints.NotNull;

public interface HabitationsService {
    Iterable<Habitation> findAllHabitations();

    Habitation findHabitationById(@NotNull Long id);

    Habitation addHabitation(@NotNull Habitation habitation);

    Habitation updateHabitation(@NotNull Habitation habitation)
            throws HabitationException;

    void deleteHabitation(@NotNull Long id)
            throws HabitationException;
}
