package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "vozilo")
public class Vozilo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvozilo", nullable = false)
    private Integer id;

    @Column(name = "registracija", nullable = false, length = 16)
    private String registracija;

    @Column(name = "naziv", nullable = false, length = 32)
    private String naziv;

    @Column(name = "proizvodjac", nullable = false, length = 32)
    private String proizvodjac;

    @Column(name = "kilometraza", nullable = false)
    private Integer kilometraza;

    @Column(name = "dnevnacijena", nullable = false, precision = 7, scale = 2)
    private BigDecimal dnevnacijena;

    @Column(name = "putdoslike", length = 1024)
    private String putdoslike;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtip", nullable = false)
    private TipVozila idtip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlokacija")
    private Lokacija idlokacija;

    @OneToMany(mappedBy = "idvozilo")
    private Set<Najam> najams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idvozilo")
    private Set<Potrazuje> potrazujes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idvozilo")
    private Set<Recenzija> recenzijas = new LinkedHashSet<>();

    public Set<Recenzija> getRecenzijas() {
        return recenzijas;
    }

    public void setRecenzijas(Set<Recenzija> recenzijas) {
        this.recenzijas = recenzijas;
    }

    public Set<Potrazuje> getPotrazujes() {
        return potrazujes;
    }

    public void setPotrazujes(Set<Potrazuje> potrazujes) {
        this.potrazujes = potrazujes;
    }

    public Set<Najam> getNajams() {
        return najams;
    }

    public void setNajams(Set<Najam> najams) {
        this.najams = najams;
    }

    public Lokacija getIdlokacija() {
        return idlokacija;
    }

    public void setIdlokacija(Lokacija idlokacija) {
        this.idlokacija = idlokacija;
    }

    public TipVozila getIdtip() {
        return idtip;
    }

    public void setIdtip(TipVozila idtip) {
        this.idtip = idtip;
    }

    public String getPutdoslike() {
        return putdoslike;
    }

    public void setPutdoslike(String putdoslike) {
        this.putdoslike = putdoslike;
    }

    public BigDecimal getDnevnacijena() {
        return dnevnacijena;
    }

    public void setDnevnacijena(BigDecimal dnevnacijena) {
        this.dnevnacijena = dnevnacijena;
    }

    public Integer getKilometraza() {
        return kilometraza;
    }

    public void setKilometraza(Integer kilometraza) {
        this.kilometraza = kilometraza;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}