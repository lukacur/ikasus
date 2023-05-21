package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Ugovor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UgovorRepository extends JpaRepository<Ugovor, Integer> {
    @Query(
    """
    SELECT EXISTS(
        SELECT u
        FROM Ugovor u
        JOIN u.izdavanjes i
        WHERE u.putdopotpisa = :sigpath AND
                i.idunajmitelj.id = :custid
    )
    """
    )
    boolean canViewSignature(@Param("custid") Integer customerId, @Param("sigpath") String pathToSignature);
}