package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "obavijest")
public class Obavijest {
    @EmbeddedId
    private ObavijestId id;

    @MapsId("idnajam")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idnajam", nullable = false)
    private Najam idnajam;

    @Column(name = "sadrzaj", nullable = false, length = 128)
    private String sadrzaj;

    @Column(name = "vrijeme", nullable = false)
    private Instant vrijeme;

    @Column(name = "videna", nullable = false)
    private Boolean videna = false;

    @Column(name = "obrisana", nullable = false)
    private Boolean obrisana = false;

    public Boolean getObrisana() {
        return obrisana;
    }

    public void setObrisana(Boolean obrisana) {
        this.obrisana = obrisana;
    }

    public Boolean getVidena() {
        return videna;
    }

    public void setVidena(Boolean videna) {
        this.videna = videna;
    }

    public Instant getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Instant vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public Najam getIdnajam() {
        return idnajam;
    }

    public void setIdnajam(Najam idnajam) {
        this.idnajam = idnajam;
    }

    public ObavijestId getId() {
        return id;
    }

    public void setId(ObavijestId id) {
        this.id = id;
    }
}