package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Zaposlenik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZaposlenikRepository extends JpaRepository<Zaposlenik, Integer> {
}