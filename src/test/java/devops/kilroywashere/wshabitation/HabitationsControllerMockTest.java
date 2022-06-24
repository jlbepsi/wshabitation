package devops.kilroywashere.wshabitation;

// Voir
// https://codefiction.net/unit-testing-crud-endpoints-of-a-spring-boot-java-web-service-api/
// https://mkyong.com/spring-boot/spring-rest-integration-test-example/

import devops.kilroywashere.wshabitation.controller.HabitationsController;
import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.Typehabitat;
import devops.kilroywashere.wshabitation.repositories.HabitationsRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HabitationsController.class)
public class HabitationsControllerMockTest {
    static Typehabitat typehabitatMaison, typehabitatAppartement;
    static Habitation habitation1, habitation2;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HabitationsRepository repository;

    @BeforeAll
    static void setUp() {
        typehabitatMaison = new Typehabitat(1, "Maison");
        typehabitatAppartement = new Typehabitat(2, "Appartement");

        habitation1 = new Habitation(1, typehabitatMaison, "Une maison",
                "Description", "adresse", 4, "image",
                5, 2, 2, 1, 80, 400);
        habitation2 = new Habitation(2, typehabitatAppartement, "new Une maison",
                "new Description", "new adresse", 4, "new image",
                7, 3, 3, 3, 100, 600);
    }

    @DisplayName("Test GET All habitations")
    @Test
    public void shouldReturn1Habitation() throws Exception {
        // GIVEN
        given(this.repository.findById(1L))
                .willReturn(Optional.of(habitation1));

        // WHEN and THEN
        this.mockMvc.perform(get("/api/habitations/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)));

        verify(repository, times(1)).findById(1L);
    }

    @DisplayName("Test GET One habitation")
    @Test
    public void shouldReturnListHabitations() throws Exception {
        // GIVEN
        given(this.repository.findAll())
                .willReturn(Lists.newArrayList(habitation1, habitation2));

        // WHEN and THEN
        this.mockMvc.perform(get("/api/habitations"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @DisplayName("Test GET none habitation")
    @Test
    public void shouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/habitations/5"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test Update none habitation")
    @Test
    public void shouldAddReturnStatus201() throws Exception {
        // TODO
    }
}
