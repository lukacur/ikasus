package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "iznajmljivac")
public class Iznajmljivac {
    @Id
    @Column(name = "idzaposlenik", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idzaposlenik")
    private Zaposlenik zaposlenik;

    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Column(name = "brojtelefona", nullable = false, length = 16)
    private String brojtelefona;

    @Column(name = "lozinka", nullable = false, length = 512)
    private String lozinka;

    @OneToMany(mappedBy = "idiznajmljivac")
    private Set<ZahtjevZaNajmom> zahtjevZaNajmoms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idiznajmljivac")
    private Set<Izdavanje> izdavanjes = new LinkedHashSet<>();

    public Set<Izdavanje> getIzdavanjes() {
        return izdavanjes;
    }

    public void setIzdavanjes(Set<Izdavanje> izdavanjes) {
        this.izdavanjes = izdavanjes;
    }

    public Set<ZahtjevZaNajmom> getZahtjevZaNajmoms() {
        return zahtjevZaNajmoms;
    }

    public void setZahtjevZaNajmoms(Set<ZahtjevZaNajmom> zahtjevZaNajmoms) {
        this.zahtjevZaNajmoms = zahtjevZaNajmoms;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getBrojtelefona() {
        return brojtelefona;
    }

    public void setBrojtelefona(String brojtelefona) {
        this.brojtelefona = brojtelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Zaposlenik getZaposlenik() {
        return zaposlenik;
    }

    public void setZaposlenik(Zaposlenik zaposlenik) {
        this.zaposlenik = zaposlenik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}