package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "upravitelj_poslovnice")
public class UpraviteljPoslovnice {
    @Id
    @Column(name = "idzaposlenik", nullable = false)
    private Integer id;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idzaposlenik")
    private Zaposlenik zaposlenik;

    @Column(name = "korisnickoime", nullable = false, length = 16)
    private String korisnickoime;

    @Column(name = "lozinka", nullable = false, length = 512)
    private String lozinka;

    @Column(name = "upraviteljod", nullable = false)
    private LocalDate upraviteljod;

    @Column(name = "upraviteljdo")
    private LocalDate upraviteljdo;

    public LocalDate getUpraviteljdo() {
        return upraviteljdo;
    }

    public void setUpraviteljdo(LocalDate upraviteljdo) {
        this.upraviteljdo = upraviteljdo;
    }

    public LocalDate getUpraviteljod() {
        return upraviteljod;
    }

    public void setUpraviteljod(LocalDate upraviteljod) {
        this.upraviteljod = upraviteljod;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
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