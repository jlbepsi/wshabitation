package devops.kilroywashere.wshabitation.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Typehabitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Le libellé doit être renseigné")
    private String libelle;

    public Typehabitat() {}
    public Typehabitat(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}