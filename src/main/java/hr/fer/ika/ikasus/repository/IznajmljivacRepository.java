package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Iznajmljivac;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IznajmljivacRepository extends JpaRepository<Iznajmljivac, Integer> {
}