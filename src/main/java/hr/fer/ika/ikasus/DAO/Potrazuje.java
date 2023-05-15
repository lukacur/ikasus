package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

@Entity
@Table(name = "potrazuje")
public class Potrazuje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpotraznja", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvozilo")
    private Vozilo idvozilo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idzahtjev")
    private ZahtjevZaNajmom idzahtjev;

    public ZahtjevZaNajmom getIdzahtjev() {
        return idzahtjev;
    }

    public void setIdzahtjev(ZahtjevZaNajmom idzahtjev) {
        this.idzahtjev = idzahtjev;
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

    //TODO Reverse Engineering! Migrate other columns to the entity
}