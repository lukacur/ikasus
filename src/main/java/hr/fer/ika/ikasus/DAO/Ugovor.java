package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ugovor")
public class Ugovor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idugovor", nullable = false)
    private Integer id;

    @Column(name = "oznugovor", nullable = false, length = 64)
    private String oznugovor;

    @Column(name = "sadrzaj", nullable = false)
    private String sadrzaj;

    @Column(name = "naslov", nullable = false, length = 128)
    private String naslov;

    @Column(name = "putdopotpisa", length = 1024)
    private String putdopotpisa;

    @Column(name = "vrijemepotpisa")
    private Instant vrijemepotpisa;

    @Column(name = "ugovorenacijena", precision = 7, scale = 2)
    private BigDecimal ugovorenacijena;

    @OneToMany(mappedBy = "idugovor")
    private Set<Izdavanje> izdavanjes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idugovor")
    private Set<Najam> najams = new LinkedHashSet<>();

    public Set<Najam> getNajams() {
        return najams;
    }

    public void setNajams(Set<Najam> najams) {
        this.najams = najams;
    }

    public Set<Izdavanje> getIzdavanjes() {
        return izdavanjes;
    }

    public void setIzdavanjes(Set<Izdavanje> izdavanjes) {
        this.izdavanjes = izdavanjes;
    }

    public BigDecimal getUgovorenacijena() {
        return ugovorenacijena;
    }

    public void setUgovorenacijena(BigDecimal ugovorenacijena) {
        this.ugovorenacijena = ugovorenacijena;
    }

    public Instant getVrijemepotpisa() {
        return vrijemepotpisa;
    }

    public void setVrijemepotpisa(Instant vrijemepotpisa) {
        this.vrijemepotpisa = vrijemepotpisa;
    }

    public String getPutdopotpisa() {
        return putdopotpisa;
    }

    public void setPutdopotpisa(String putdopotpisa) {
        this.putdopotpisa = putdopotpisa;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public String getOznugovor() {
        return oznugovor;
    }

    public void setOznugovor(String oznugovor) {
        this.oznugovor = oznugovor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}