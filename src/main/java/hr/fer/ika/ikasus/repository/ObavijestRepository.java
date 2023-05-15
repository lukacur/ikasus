package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Obavijest;
import hr.fer.ika.ikasus.DAO.ObavijestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObavijestRepository extends JpaRepository<Obavijest, ObavijestId> {
}