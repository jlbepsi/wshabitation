package devops.kilroywashere.wshabitation.models;

import javax.persistence.*;

@Entity
@Table(name = "locationro")
public class LocationRO {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_utilisateur", nullable = false)
    private Integer idUtilisateur;

    @Column(name = "id_habitation", nullable = false)
    private Integer idHabitation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "optionpayante_id", nullable = false)
    private Optionpayante optionpayante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdHabitation() {
        return idHabitation;
    }

    public void setIdHabitation(Integer idHabitation) {
        this.idHabitation = idHabitation;
    }

    public Optionpayante getOptionpayante() {
        return optionpayante;
    }

    public void setOptionpayante(Optionpayante optionpayante) {
        this.optionpayante = optionpayante;
    }

}