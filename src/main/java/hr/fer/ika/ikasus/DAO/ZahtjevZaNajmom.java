package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "zahtjev_za_najmom")
public class ZahtjevZaNajmom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idzahtjev", nullable = false)
    private Integer id;

    @Column(name = "status", nullable = false, length = 16)
    private String status;

    @Column(name = "vrijemezahtjeva", nullable = false)
    private Instant vrijemezahtjeva;

    @Column(name = "obraden", nullable = false)
    private Boolean obraden = false;

    @Column(name = "vrijemeobrada")
    private Instant vrijemeobrada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idiznajmljivac")
    private Iznajmljivac idiznajmljivac;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idunajmitelj", nullable = false)
    private Unajmitelj idunajmitelj;

    @OneToMany(mappedBy = "idzahtjev")
    private Set<Potrazuje> potrazujes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idzahtjev")
    private Set<Ugovor> ugovors = new LinkedHashSet<>();

    public Set<Ugovor> getUgovors() {
        return ugovors;
    }

    public void setUgovors(Set<Ugovor> ugovors) {
        this.ugovors = ugovors;
    }

    public Set<Potrazuje> getPotrazujes() {
        return potrazujes;
    }

    public void setPotrazujes(Set<Potrazuje> potrazujes) {
        this.potrazujes = potrazujes;
    }

    public Unajmitelj getIdunajmitelj() {
        return idunajmitelj;
    }

    public void setIdunajmitelj(Unajmitelj idunajmitelj) {
        this.idunajmitelj = idunajmitelj;
    }

    public Iznajmljivac getIdiznajmljivac() {
        return idiznajmljivac;
    }

    public void setIdiznajmljivac(Iznajmljivac idiznajmljivac) {
        this.idiznajmljivac = idiznajmljivac;
    }

    public Instant getVrijemeobrada() {
        return vrijemeobrada;
    }

    public void setVrijemeobrada(Instant vrijemeobrada) {
        this.vrijemeobrada = vrijemeobrada;
    }

    public Boolean getObraden() {
        return obraden;
    }

    public void setObraden(Boolean obraden) {
        this.obraden = obraden;
    }

    public Instant getVrijemezahtjeva() {
        return vrijemezahtjeva;
    }

    public void setVrijemezahtjeva(Instant vrijemezahtjeva) {
        this.vrijemezahtjeva = vrijemezahtjeva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}