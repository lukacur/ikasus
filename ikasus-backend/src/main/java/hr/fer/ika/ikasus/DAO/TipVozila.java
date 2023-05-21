package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tip_vozila")
public class TipVozila {
    @Id
    @Column(name = "idtip", nullable = false, length = 16)
    private String id;

    @Column(name = "nazivtip", nullable = false, length = 128)
    private String nazivtip;

    @Column(name = "kategorija", nullable = false, length = 8)
    private String kategorija;

    @OneToMany(mappedBy = "idtip")
    private Set<Vozilo> vozilos = new LinkedHashSet<>();

    public Set<Vozilo> getVozilos() {
        return vozilos;
    }

    public void setVozilos(Set<Vozilo> vozilos) {
        this.vozilos = vozilos;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getNazivtip() {
        return nazivtip;
    }

    public void setNazivtip(String nazivtip) {
        this.nazivtip = nazivtip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}