package devops.kilroywashere.wshabitation.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/*
Json Infinite loop : https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
Option 1

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
 */
@Entity
public class Habitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Typehabitat typehabitat;

    /**
     * Liste des items pour cet habitat
     */
    @ManyToMany()
    @JoinTable(
            name = "habitation_item",
            joinColumns = { @JoinColumn(name = "habitation_id",
                                        referencedColumnName = "id") },
            inverseJoinColumns = {@JoinColumn(name = "item_id",
                                        referencedColumnName = "id") }
    )
    private Set<Item> items = new HashSet<>();

    /*
        Json Infinite loop
        Version 2
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "habitation")
    private Set<HabitationOptionpayante> optionpayantes = new HashSet<>();

    @NotBlank(message = "Le libellé doit être renseigné")
    private String libelle;

    private String description;

    @NotBlank(message = "L'adresse doit être renseignée")
    private String adresse;

    private Integer idVille;

    @NotBlank(message = "L'image doit être une URL valide")
    private String image;

    @Min(message = "Au moins 1 habitant", value = 1)
    private Integer habitantsmax;

    @Min(message = "Au moins 1 chambre", value = 1)
    private Integer chambres;

    @Min(message = "Au moins 1 lit", value = 1)
    private Integer lits;

    @Min(message = "Au moins 1 salle de bain", value = 1)
    private Integer sdb;

    @Min(message = "La superfice doit être supérieure ou égale à 10 m²", value = 10)
    private Integer superficie;

    @Min(message = "Le prix doit être supérieur ou égal à 100 €", value = 100)
    private double prixmois;

    public Habitation() {}
    public Habitation(int id, Typehabitat typehabitat, String libelle, String description, String adresse, int idVille,
                      String image, int habitantsmax, int chambres, int lits, int sdb, int superficie, double prixmois) {
        this.id = id;
        this.typehabitat = typehabitat;
        this.libelle = libelle;
        this.description = description;
        this.adresse = adresse;
        this.idVille = idVille;
        this.image = image;
        this.habitantsmax = habitantsmax;
        this.chambres = chambres;
        this.lits = lits;
        this.sdb = sdb;
        this.superficie = superficie;
        this.prixmois = prixmois;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Typehabitat getTypehabitat() {
        return typehabitat;
    }
    public void setTypehabitat(Typehabitat typehabitat) {
        this.typehabitat = typehabitat;
    }

    public Set<Item> getItems() {
        return items;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<HabitationOptionpayante> getOptionpayantes() {
        return optionpayantes;
    }

    public void setOptionpayantes(Set<HabitationOptionpayante> optionpayantes) {
        this.optionpayantes = optionpayantes;
    }

    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getIdVille() {
        return idVille;
    }
    public void setIdVille(Integer idVille) {
        this.idVille = idVille;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public Integer getHabitantsmax() {
        return habitantsmax;
    }
    public void setHabitantsmax(Integer habitantsmax) {
        this.habitantsmax = habitantsmax;
    }

    public Integer getChambres() {
        return chambres;
    }
    public void setChambres(Integer chambres) {
        this.chambres = chambres;
    }

    public Integer getLits() {
        return lits;
    }
    public void setLits(Integer lits) {
        this.lits = lits;
    }

    public Integer getSdb() {
        return sdb;
    }
    public void setSdb(Integer sdb) {
        this.sdb = sdb;
    }

    public Integer getSuperficie() {
        return superficie;
    }
    public void setSuperficie(Integer superficie) {
        this.superficie = superficie;
    }

    public double getPrixmois() {
        return prixmois;
    }
    public void setPrixmois(double prixmois) {
        this.prixmois = prixmois;
    }

    @Override
    public String toString() {
        return String.format("Habitation=[id:%d, libelle:%s, typeHabitat:%s]", id, libelle, typehabitat.getLibelle());
    }

    /**
     * Mise à jour de l'objet d'après l'habitation passée en paramètre
     * @param habitation Les nouvelles valeurs de l'habitation
     */
    public void updateFrom(@NotNull Habitation habitation) {
        this.libelle = habitation.libelle;
        this.description = habitation.description;
        this.image = habitation.image;
        this.habitantsmax = habitation.habitantsmax;
        this.chambres = habitation.chambres;
        this.lits = habitation.lits;
        this.sdb = habitation.sdb;
        this.superficie = habitation.superficie;
        this.prixmois = habitation.prixmois;
    }
}