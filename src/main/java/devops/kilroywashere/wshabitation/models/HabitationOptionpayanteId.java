package devops.kilroywashere.wshabitation.models;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HabitationOptionpayanteId implements Serializable {
    private static final long serialVersionUID = -6814869977361972320L;
    @Column(name = "habitation_id", nullable = false)
    private Integer habitationId;

    @Column(name = "optionpayante_id", nullable = false)
    private Integer optionpayanteId;

    public Integer getHabitationId() {
        return habitationId;
    }

    public void setHabitationId(Integer habitationId) {
        this.habitationId = habitationId;
    }

    public Integer getOptionpayanteId() {
        return optionpayanteId;
    }

    public void setOptionpayanteId(Integer optionpayanteId) {
        this.optionpayanteId = optionpayanteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HabitationOptionpayanteId entity = (HabitationOptionpayanteId) o;
        return Objects.equals(this.optionpayanteId, entity.optionpayanteId) &&
                Objects.equals(this.habitationId, entity.habitationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionpayanteId, habitationId);
    }

}