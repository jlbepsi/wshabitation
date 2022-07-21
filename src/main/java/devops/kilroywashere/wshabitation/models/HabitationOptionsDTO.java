package devops.kilroywashere.wshabitation.models;

import java.util.List;

public class HabitationOptionsDTO {

    public static class HabitationOptionPrixDTO  {
        private int optionId;
        private Double prix;

        public int getOptionId() {
            return optionId;
        }

        public void setOptionId(int optionId) {
            this.optionId = optionId;
        }

        public Double getPrix() {
            return prix;
        }

        public void setPrix(Double prix) {
            this.prix = prix;
        }
    }

    private int habitationId;
    private List<HabitationOptionPrixDTO> optionIdPrixs;

    public int getHabitationId() {
        return habitationId;
    }
    public void setHabitationId(int habitationId) {
        this.habitationId = habitationId;
    }

    public List<HabitationOptionPrixDTO> getOptionIdPrixs() {
        return optionIdPrixs;
    }

    public void setOptionIdPrixs(List<HabitationOptionPrixDTO> optionIdPrixs) {
        this.optionIdPrixs = optionIdPrixs;
    }
}
