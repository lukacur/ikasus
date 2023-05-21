package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "najam")
public class Najam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnajam", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvozilo")
    private Vozilo idvozilo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idugovor")
    private Ugovor idugovor;

    @Column(name = "vrijemeod", nullable = false)
    private Instant vrijemeod;

    @Column(name = "vrijemedo")
    private Instant vrijemedo;

    @Column(name = "prijedeno")
    private Integer prijedeno;

    @Column(name = "aktivan", nullable = false)
    private Boolean aktivan = false;

    @OneToMany(mappedBy = "idnajam")
    private Set<Obavijest> obavijests = new LinkedHashSet<>();

    public Set<Obavijest> getObavijests() {
        return obavijests;
    }

    public void setObavijests(Set<Obavijest> obavijests) {
        this.obavijests = obavijests;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Integer getPrijedeno() {
        return prijedeno;
    }

    public void setPrijedeno(Integer prijedeno) {
        this.prijedeno = prijedeno;
    }

    public Instant getVrijemedo() {
        return vrijemedo;
    }

    public void setVrijemedo(Instant vrijemedo) {
        this.vrijemedo = vrijemedo;
    }

    public Instant getVrijemeod() {
        return vrijemeod;
    }

    public void setVrijemeod(Instant vrijemeod) {
        this.vrijemeod = vrijemeod;
    }

    public Ugovor getIdugovor() {
        return idugovor;
    }

    public void setIdugovor(Ugovor idugovor) {
        this.idugovor = idugovor;
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