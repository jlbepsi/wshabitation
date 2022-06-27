package devops.kilroywashere.wshabitation.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class HabitationItemsDTO {
    @Min(1)
    private int habitationId;
    @NotEmpty(message = "La liste ne doit pas être vide")
    private List<Integer> itemIds;

    public int getHabitationId() {
        return habitationId;
    }
    public void setHabitationId(int habitationId) {
        this.habitationId = habitationId;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }
    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }
}
