package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "izdavanje")
public class Izdavanje {
    @EmbeddedId
    private IzdavanjeId id;

    @MapsId("idunajmitelj")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idunajmitelj", nullable = false)
    private Unajmitelj idunajmitelj;

    @MapsId("idugovor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idugovor", nullable = false)
    private Ugovor idugovor;

    @Column(name = "datumizdavanja", nullable = false)
    private LocalDate datumizdavanja;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idiznajmljivac", nullable = false)
    private Iznajmljivac idiznajmljivac;

    public Iznajmljivac getIdiznajmljivac() {
        return idiznajmljivac;
    }

    public void setIdiznajmljivac(Iznajmljivac idiznajmljivac) {
        this.idiznajmljivac = idiznajmljivac;
    }

    public LocalDate getDatumizdavanja() {
        return datumizdavanja;
    }

    public void setDatumizdavanja(LocalDate datumizdavanja) {
        this.datumizdavanja = datumizdavanja;
    }

    public Ugovor getIdugovor() {
        return idugovor;
    }

    public void setIdugovor(Ugovor idugovor) {
        this.idugovor = idugovor;
    }

    public Unajmitelj getIdunajmitelj() {
        return idunajmitelj;
    }

    public void setIdunajmitelj(Unajmitelj idunajmitelj) {
        this.idunajmitelj = idunajmitelj;
    }

    public IzdavanjeId getId() {
        return id;
    }

    public void setId(IzdavanjeId id) {
        this.id = id;
    }
}