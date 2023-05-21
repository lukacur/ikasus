package hr.fer.ika.ikasus.repository;

import hr.fer.ika.ikasus.DAO.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface VoziloRepository extends JpaRepository<Vozilo, Integer> {
    @Query(
        """
        SELECT v
        FROM Vozilo v
        JOIN v.najams n
        WHERE n.vrijemeod NOT BETWEEN :timefrom AND :timeto AND
                n.vrijemedo NOT BETWEEN :timefrom AND :timeto AND
                n.vrijemedo IS NOT NULL AND
                :timefrom <= :timeto AND
                (:timeto <= n.vrijemeod OR :timefrom >= n.vrijemedo)
        """
    )
    List<Vozilo> getAvailable(@Param("timefrom")Instant timeFrom, @Param("timeto") Instant timeTo);

    @Query(
        """
        SELECT EXISTS(
            SELECT v
            FROM Vozilo v
            JOIN v.najams n
            WHERE v.id = :vid AND
                    n.vrijemeod NOT BETWEEN :timefrom AND :timeto AND
                    n.vrijemedo NOT BETWEEN :timefrom AND :timeto AND
                    n.vrijemedo IS NOT NULL AND
                    :timefrom <= :timeto AND
                    (:timeto <= n.vrijemeod OR :timefrom >= n.vrijemedo)
        )
        """
    )
    boolean isAvailable(
            @Param("vid") Integer vehicleId,
            @Param("timefrom")Instant timeFrom,
            @Param("timeto") Instant timeTo
    );
}