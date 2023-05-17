package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "lokacija")
public class Lokacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlokacija", nullable = false)
    private Integer id;

    @Column(name = "adresa", length = 128)
    private String adresa;

    @Column(name = "pbr", nullable = false, length = 32)
    private String pbr;

    @Column(name = "grad", nullable = false, length = 64)
    private String grad;

    @Column(name = "drzava", nullable = false, length = 64)
    private String drzava;

    @OneToMany(mappedBy = "idlokacija")
    private Set<Zaposlenik> zaposleniks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idlokacija")
    private Set<Vozilo> vozilos = new LinkedHashSet<>();

    public Set<Vozilo> getVozilos() {
        return vozilos;
    }

    public void setVozilos(Set<Vozilo> vozilos) {
        this.vozilos = vozilos;
    }

    public Set<Zaposlenik> getZaposleniks() {
        return zaposleniks;
    }

    public void setZaposleniks(Set<Zaposlenik> zaposleniks) {
        this.zaposleniks = zaposleniks;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getPbr() {
        return pbr;
    }

    public void setPbr(String pbr) {
        this.pbr = pbr;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}