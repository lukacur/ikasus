package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Obavijest;
import hr.fer.ika.ikasus.DAO.ObavijestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ObavijestRepository extends JpaRepository<Obavijest, ObavijestId> {
    @Query(
        """
        SELECT o
        FROM Obavijest o
            JOIN o.idnajam n
            JOIN n.idugovor u
            JOIN u.idzahtjev z
            JOIN z.idunajmitelj un
        WHERE un.id = :customerid
        """
    )
    List<Obavijest> getForCustomer(@Param("customerid") Integer customerId);

    @Query(value =
        """
        SELECT MAX(idobavijest) + 1
        FROM obavijest
        WHERE idnajam = :rentalid
        """,
        nativeQuery = true
    )
    Integer getNextIdForRental(@Param("rentalid") Integer rentalId);

    @Query(value =
        """
        SELECT EXISTS (
            SELECT o
            FROM Obavijest o
            WHERE o.idnajam.id = :rentalid AND
                    o.vrijeme BETWEEN :begindate AND CURRENT_DATE
        )
        """
    )
    boolean wasNotifiedRecently(
            @Param("rentalid") Integer rentalId,
            @Param("begindate") Instant beginDate
    );
}