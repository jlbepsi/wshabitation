package devops.kilroywashere.wshabitation.repositories;

import org.springframework.data.repository.CrudRepository;

import devops.kilroywashere.wshabitation.models.HabitationOptionpayante;
import devops.kilroywashere.wshabitation.models.HabitationOptionpayanteId;

import java.util.List;

public interface HabitationOptionsRepository extends CrudRepository<HabitationOptionpayante, HabitationOptionpayanteId> {
    List<HabitationOptionpayante> findHabitationOptionpayantesByHabitation_Id(int id);
}
