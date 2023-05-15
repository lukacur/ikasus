package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Izdavanje;
import hr.fer.ika.ikasus.DAO.IzdavanjeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IzdavanjeRepository extends JpaRepository<Izdavanje, IzdavanjeId> {
}