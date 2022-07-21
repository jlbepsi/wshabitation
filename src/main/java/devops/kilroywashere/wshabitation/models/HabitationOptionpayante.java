package devops.kilroywashere.wshabitation.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

/*
Json Infinite loop : https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
Option 1

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
 */
@Entity
@Table(name = "habitation_optionpayante")
public class HabitationOptionpayante {
    @EmbeddedId
    private HabitationOptionpayanteId id;


    /*
        Json Infinite loop
        Version 2
     */
    @JsonBackReference
    @ManyToOne
    @MapsId("habitationId")
    @JoinColumn(name = "habitation_id")
    private Habitation habitation;

    @ManyToOne
    @MapsId("optionpayanteId")
    @JoinColumn(name = "optionpayante_id")
    private Optionpayante optionpayante;

    @Column(name = "prix", nullable = false)
    private Double prix;

    public HabitationOptionpayanteId getId() {
        return id;
    }
    public void setId(HabitationOptionpayanteId id) {
        this.id = id;
    }

    public Habitation getHabitation() {
        return habitation;
    }
    public void setHabitation(Habitation habitation) {
        this.habitation = habitation;
    }

    public Optionpayante getOptionpayante() {
        return optionpayante;
    }
    public void setOptionpayante(Optionpayante optionpayante) {
        this.optionpayante = optionpayante;
    }

    public Double getPrix() {
        return prix;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }

}