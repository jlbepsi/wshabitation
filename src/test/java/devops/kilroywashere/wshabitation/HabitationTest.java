package devops.kilroywashere.wshabitation;

import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.Typehabitat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitationTest {

    static Typehabitat typehabitatMaison, typehabitatAppartement;

    @BeforeAll
    static void setUp() {
        typehabitatMaison = new Typehabitat(1, "Maison");
        typehabitatAppartement = new Typehabitat(2, "Appartement");
    }

    @Test
    public void update_shouldUpdateSomeFields() {
        // GIVEN
        Habitation habitation = new Habitation(1, typehabitatMaison, "Une maison",
                "Description", "adresse", 4, "image",
                5, 2, 2, 1, 80, 400);
        Habitation habitationUpdate = new Habitation(2, typehabitatAppartement, "new Une maison",
                "new Description", "new adresse", 4, "new image",
                7, 3, 3, 3, 100, 600);

        // WHEN
        habitation.updateFrom(habitationUpdate);

        // THEN
        // Attributs devant rester fixe
        assertThat(habitation.getId()).isEqualTo(1);
        assertThat(habitation.getTypehabitat()).isEqualTo(typehabitatMaison);
        assertThat(habitation.getAdresse()).isEqualTo("adresse");
        assertThat(habitation.getIdVille()).isEqualTo(4);
        // Les autres attributs
        assertThat(habitation.getLibelle()).isEqualTo("new Une maison");
        assertThat(habitation.getDescription()).isEqualTo("new Description");
        assertThat(habitation.getImage()).isEqualTo("new image");
        assertThat(habitation.getHabitantsmax()).isEqualTo(7);
        assertThat(habitation.getChambres()).isEqualTo(3);
        assertThat(habitation.getLits()).isEqualTo(3);
        assertThat(habitation.getSdb()).isEqualTo(3);
        assertThat(habitation.getSuperficie()).isEqualTo(100);
        assertThat(habitation.getPrixmois()).isEqualTo(600);
    }
}
