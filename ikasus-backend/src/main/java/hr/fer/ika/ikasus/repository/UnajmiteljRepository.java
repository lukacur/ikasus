package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Unajmitelj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnajmiteljRepository extends JpaRepository<Unajmitelj, Integer> {
    Optional<Unajmitelj> findByEmail(String email);
}