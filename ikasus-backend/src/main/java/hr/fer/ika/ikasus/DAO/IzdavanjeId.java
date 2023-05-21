package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IzdavanjeId implements Serializable {
    private static final long serialVersionUID = 8840070830077350383L;
    @Column(name = "idunajmitelj", nullable = false)
    private Integer idunajmitelj;
    @Column(name = "idugovor", nullable = false)
    private Integer idugovor;

    public Integer getIdugovor() {
        return idugovor;
    }

    public void setIdugovor(Integer idugovor) {
        this.idugovor = idugovor;
    }

    public Integer getIdunajmitelj() {
        return idunajmitelj;
    }

    public void setIdunajmitelj(Integer idunajmitelj) {
        this.idunajmitelj = idunajmitelj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idugovor, idunajmitelj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IzdavanjeId entity = (IzdavanjeId) o;
        return Objects.equals(this.idugovor, entity.idugovor) &&
                Objects.equals(this.idunajmitelj, entity.idunajmitelj);
    }
}