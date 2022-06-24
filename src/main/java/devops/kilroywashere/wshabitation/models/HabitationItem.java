package devops.kilroywashere.wshabitation.models;

import javax.persistence.*;

@Entity
@Table(name = "habitation_item")
public class HabitationItem {
    @EmbeddedId
    private HabitationItemId id;

    @MapsId("habitationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitation_id", nullable = false)
    private Habitation habitation;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public HabitationItemId getId() {
        return id;
    }

    public void setId(HabitationItemId id) {
        this.id = id;
    }

    public Habitation getHabitation() {
        return habitation;
    }

    public void setHabitation(Habitation habitation) {
        this.habitation = habitation;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}