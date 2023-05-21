package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Iznajmljivac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IznajmljivacRepository extends JpaRepository<Iznajmljivac, Integer> {
    Optional<Iznajmljivac> findByEmail(String email);
}