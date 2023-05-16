package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "unajmitelj")
public class Unajmitelj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idunajmitelj", nullable = false)
    private Integer id;

    @Column(name = "ime", nullable = false, length = 32)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 32)
    private String prezime;

    @Column(name = "oib", nullable = false, length = 16)
    private String oib;

    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Column(name = "lozinka", nullable = false, length = 512)
    private String lozinka;

    @Column(name = "brojtelefona", nullable = false, length = 16)
    private String brojtelefona;

    @OneToMany(mappedBy = "idunajmitelj")
    private Set<ZahtjevZaNajmom> zahtjevZaNajmoms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idunajmitelj")
    private Set<Izdavanje> izdavanjes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idunajmitelj")
    private Set<Recenzija> recenzijas = new LinkedHashSet<>();

    public Set<Recenzija> getRecenzijas() {
        return recenzijas;
    }

    public void setRecenzijas(Set<Recenzija> recenzijas) {
        this.recenzijas = recenzijas;
    }

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

    public String getBrojtelefona() {
        return brojtelefona;
    }

    public void setBrojtelefona(String brojtelefona) {
        this.brojtelefona = brojtelefona;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}