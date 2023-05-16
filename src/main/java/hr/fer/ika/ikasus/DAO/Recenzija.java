package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

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

    //TODO Reverse Engineering! Migrate other columns to the entity
}