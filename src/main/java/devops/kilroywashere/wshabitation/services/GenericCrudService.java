package devops.kilroywashere.wshabitation.services;

import devops.kilroywashere.wshabitation.models.HabitationException;

import javax.validation.constraints.NotNull;

public interface GenericCrudService<T> {
    Iterable<T> findAll();

    T findById(@NotNull Integer id);

    T add(@NotNull T typehabitat);

    T update(@NotNull T typehabitat)
            throws HabitationException;

    void delete(@NotNull Integer id)
            throws HabitationException;
}
