package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.UpraviteljPoslovnice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpraviteljPoslovniceRepository extends JpaRepository<UpraviteljPoslovnice, Integer> {
    Optional<UpraviteljPoslovnice> findByKorisnickoime(String korisnickoime);
}