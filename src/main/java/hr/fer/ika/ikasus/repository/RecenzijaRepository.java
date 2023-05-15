package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Integer> {
}