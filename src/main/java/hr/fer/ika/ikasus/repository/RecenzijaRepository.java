package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Recenzija;
import hr.fer.ika.ikasus.DAO.Unajmitelj;
import hr.fer.ika.ikasus.DAO.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Integer> {
    List<Recenzija> findByIdvozilo(Vozilo v);
    List<Recenzija> findByIdunajmitelj(Unajmitelj u);
}