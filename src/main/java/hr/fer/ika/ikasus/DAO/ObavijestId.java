package hr.fer.ika.ikasus.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ObavijestId implements Serializable {
    private static final long serialVersionUID = -1708989680743881870L;
    @Column(name = "idobavijest", nullable = false)
    private Integer idobavijest;
    @Column(name = "idnajam", nullable = false)
    private Integer idnajam;

    public Integer getIdnajam() {
        return idnajam;
    }

    public void setIdnajam(Integer idnajam) {
        this.idnajam = idnajam;
    }

    public Integer getIdobavijest() {
        return idobavijest;
    }

    public void setIdobavijest(Integer idobavijest) {
        this.idobavijest = idobavijest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idnajam, idobavijest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ObavijestId entity = (ObavijestId) o;
        return Objects.equals(this.idnajam, entity.idnajam) &&
                Objects.equals(this.idobavijest, entity.idobavijest);
    }
}