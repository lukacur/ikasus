package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

@Entity
@Table(name = "zaposlenik")
public class Zaposlenik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idzaposlenik", nullable = false)
    private Integer id;

    @Column(name = "oib", nullable = false, length = 16)
    private String oib;

    @Column(name = "ime", nullable = false, length = 32)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 32)
    private String prezime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlokacija")
    private Lokacija idlokacija;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "zaposlenik")
    private UpraviteljPoslovnice upraviteljPoslovnice;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "zaposlenik")
    private Iznajmljivac iznajmljivac;

    public Iznajmljivac getIznajmljivac() {
        return iznajmljivac;
    }

    public void setIznajmljivac(Iznajmljivac iznajmljivac) {
        this.iznajmljivac = iznajmljivac;
    }

    public UpraviteljPoslovnice getUpraviteljPoslovnice() {
        return upraviteljPoslovnice;
    }

    public void setUpraviteljPoslovnice(UpraviteljPoslovnice upraviteljPoslovnice) {
        this.upraviteljPoslovnice = upraviteljPoslovnice;
    }

    public Lokacija getIdlokacija() {
        return idlokacija;
    }

    public void setIdlokacija(Lokacija idlokacija) {
        this.idlokacija = idlokacija;
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

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}