package devops.kilroywashere.wshabitation.models;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HabitationItemId implements Serializable {
    private static final long serialVersionUID = -1671153414346910427L;
    @Column(name = "habitation_id", nullable = false)
    private Integer habitationId;

    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    public Integer getHabitationId() {
        return habitationId;
    }

    public void setHabitationId(Integer habitationId) {
        this.habitationId = habitationId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HabitationItemId entity = (HabitationItemId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.habitationId, entity.habitationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, habitationId);
    }

}