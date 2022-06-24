package devops.kilroywashere.wshabitation.models;

import javax.persistence.*;

@Entity
@Table(name = "habitation_optionpayante")
public class HabitationOptionpayante {
    @EmbeddedId
    private HabitationOptionpayanteId id;

    @MapsId("habitationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitation_id", nullable = false)
    private Habitation habitation;

    @MapsId("optionpayanteId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "optionpayante_id", nullable = false)
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