package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Najam;
import hr.fer.ika.ikasus.DAO.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NajamRepository extends JpaRepository<Najam, Integer> {
    List<Najam> findByIdvozilo(Vozilo vozilo);
}