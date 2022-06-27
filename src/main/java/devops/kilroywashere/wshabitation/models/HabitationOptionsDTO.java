package devops.kilroywashere.wshabitation.models;

import java.util.List;

public class HabitationOptionsDTO {
    private int habitationId;
    private List<Integer> optionIds;

    public int getHabitationId() {
        return habitationId;
    }
    public void setHabitationId(int habitationId) {
        this.habitationId = habitationId;
    }

    public List<Integer> getOptionIds() {
        return optionIds;
    }
    public void setOptionIds(List<Integer> optionIds) {
        this.optionIds = optionIds;
    }
}
