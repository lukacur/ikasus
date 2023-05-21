package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Lokacija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LokacijaRepository extends JpaRepository<Lokacija, Integer> {
}