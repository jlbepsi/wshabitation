package devops.kilroywashere.wshabitation;

// Voir
// https://codefiction.net/unit-testing-crud-endpoints-of-a-spring-boot-java-web-service-api/
// https://mkyong.com/spring-boot/spring-rest-integration-test-example/

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.kilroywashere.wshabitation.controller.HabitationsController;
import devops.kilroywashere.wshabitation.models.Habitation;
import devops.kilroywashere.wshabitation.models.HabitationException;
import devops.kilroywashere.wshabitation.models.HabitationNotFoundException;
import devops.kilroywashere.wshabitation.models.Typehabitat;
import devops.kilroywashere.wshabitation.services.HabitationsServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private HabitationsServiceImpl service;

    // Mapper JSON
    private final ObjectMapper mapper = new ObjectMapper();

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
    public void getAll_shouldReturnListHabitations() throws Exception {
        // GIVEN
        given(service.findAllHabitations())
                .willReturn(Lists.newArrayList(habitation1, habitation2));

        // WHEN and THEN
        this.mockMvc.perform(get("/api/habitations"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @DisplayName("Test GET One habitation")
    @Test
    public void getOne_shouldReturn1Habitation() throws Exception {
        // GIVEN
        given(service.findHabitationById(isA(Integer.class)))
                .willReturn(habitation1);

        // WHEN and THEN
        this.mockMvc.perform(get("/api/habitations/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)));

        verify(service, times(1)).findHabitationById(1);
    }

    @DisplayName("Test GET none habitation")
    @Test
    public void getOneUnknown_shouldReturnNotFound() throws Exception {
        this.mockMvc.perform(get("/api/habitations/5"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test Add a habitation")
    @Test
    public void add_shouldReturnStatusCreated() throws Exception {
        // GIVEN
        given(service.addHabitation(ArgumentMatchers.any(Habitation.class)))
                .willReturn(habitation1);
        // Objet Habitation au format JSON
        String json = mapper.writeValueAsString(habitation1);

        // WHEN and THEN
        this.mockMvc.perform(
                post("/api/habitations")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("Test Update a habitation")
    @Test
    public void update_shouldReturnOk() throws Exception {
        // GIVEN
        given(service.updateHabitation(ArgumentMatchers.any(Habitation.class)))
                .willReturn(habitation1);
        // Objet Habitation au format JSON
        String json = mapper.writeValueAsString(habitation1);

        // WHEN and THEN
        this.mockMvc.perform(
                put("/api/habitations/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @DisplayName("Test Update a unknown habitation")
    @Test
    public void updateUnknown_shouldReturnNotFound() throws Exception {
        // GIVEN
        given(service.updateHabitation(ArgumentMatchers.any(Habitation.class)))
                .willThrow(new HabitationNotFoundException());
        // Objet Habitation au format JSON
        String json = mapper.writeValueAsString(habitation1);

        // WHEN and THEN
        this.mockMvc.perform(
                put("/api/habitations/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test Update a unknown habitation")
    @Test
    public void updateWrong_shouldReturnBadRequest() throws Exception {
        // GIVEN
        given(service.updateHabitation(ArgumentMatchers.any(Habitation.class)))
                .willThrow(new HabitationException());
        // Objet Habitation au format JSON
        String json = mapper.writeValueAsString(habitation1);

        // WHEN and THEN
        this.mockMvc.perform(
                        put("/api/habitations/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test Delete a habitation")
    @Test
    public void delete_shouldReturnNoContent() throws Exception {
        // GIVEN
        doNothing().when(service).deleteHabitation(isA(Integer.class));

        // WHEN and THEN
        this.mockMvc.perform(delete("/api/habitations/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Test Delete an unknown habitation")
    @Test
    public void deleteUnknown_shouldReturnNotFound() throws Exception {
        // GIVEN
        doThrow(new HabitationNotFoundException()).when(service).deleteHabitation(isA(Integer.class));

        // WHEN and THEN
        this.mockMvc.perform(delete("/api/habitations/1"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test Delete a wrong habitation")
    @Test
    public void deleteWrong_shouldReturnBadRequest() throws Exception {
        // GIVEN
        doThrow(new HabitationException()).when(service).deleteHabitation(isA(Integer.class));

        // WHEN and THEN
        this.mockMvc.perform(delete("/api/habitations/1"))
                .andExpect(status().isBadRequest());
    }
}
