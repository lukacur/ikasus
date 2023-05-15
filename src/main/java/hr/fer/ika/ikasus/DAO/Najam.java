package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.*;

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