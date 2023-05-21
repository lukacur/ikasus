package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Izdavanje;
import hr.fer.ika.ikasus.DAO.IzdavanjeId;
import hr.fer.ika.ikasus.DAO.Ugovor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IzdavanjeRepository extends JpaRepository<Izdavanje, IzdavanjeId> {
    List<Izdavanje> findByIdugovor(Ugovor u);
}