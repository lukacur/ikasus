package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "recenzija")
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrecenzija", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvozilo")
    private Vozilo idvozilo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idunajmitelj")
    private Unajmitelj idunajmitelj;

    @Column(name = "sadrzaj", nullable = false, length = 256)
    private String sadrzaj;

    @Column(name = "ocjena", nullable = false)
    private Integer ocjena;

    @Column(name = "kilometraza", nullable = false)
    private Integer kilometraza;

    @Column(name = "vrijeme", nullable = false)
    private Instant vrijeme;

    public Instant getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Instant vrijeme) {
        this.vrijeme = vrijeme;
    }

    public Integer getKilometraza() {
        return kilometraza;
    }

    public void setKilometraza(Integer kilometraza) {
        this.kilometraza = kilometraza;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public Unajmitelj getIdunajmitelj() {
        return idunajmitelj;
    }

    public void setIdunajmitelj(Unajmitelj idunajmitelj) {
        this.idunajmitelj = idunajmitelj;
    }

    public Vozilo getIdvozilo() {
        return idvozilo;
    }

    public void setIdvozilo(Vozilo idvozilo) {
        this.idvozilo = idvozilo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}