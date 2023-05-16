package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Najam;
import hr.fer.ika.ikasus.DAO.Ugovor;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.repository.NajamRepository;
import hr.fer.ika.ikasus.repository.UgovorRepository;
import hr.fer.ika.ikasus.repository.VoziloRepository;
import hr.fer.ika.ikasus.service.RentalService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Luka Ćurić
 */
@Service
public class RentalServiceImpl implements RentalService {
    private final NajamRepository najamRepository;
    private final UgovorRepository ugovorRepository;
    private final VoziloRepository voziloRepository;

    public RentalServiceImpl(
            NajamRepository najamRepository,
            UgovorRepository ugovorRepository,
            VoziloRepository voziloRepository
    ) {
        this.najamRepository = najamRepository;
        this.ugovorRepository = ugovorRepository;
        this.voziloRepository = voziloRepository;
    }

    private static boolean validate(CreateRentalDetail detail, List<Najam> vehicleRentals) {
        if (detail.getTimeFrom() == null || detail.getCarId() == null || detail.getContractId() == null) {
            return false;
        }

        Date detailFrom = detail.getTimeFrom();
        Date detailTo = detail.getTimeTo();

        if (detailTo != null && detailTo.before(detailFrom)) {
            return false;
        }

        return vehicleRentals.stream().noneMatch(r -> r.getAktivan() && detail.getActive()) &&
                vehicleRentals.stream().noneMatch(r -> {
                    Date rFrom = Date.from(r.getVrijemeod());
                    Date rTo;

                    if (r.getVrijemedo() == null) {
                        if (detailTo == null) {
                            return true;
                        }

                        return detailTo.after(rFrom);
                    }

                    rTo = Date.from(r.getVrijemedo());

                    if (detailTo == null) {
                        return detailFrom.before(rTo);
                    }

                    return !(
                            detailTo.before(rFrom) || detailTo.equals(rFrom) ||
                                    detailFrom.after(rTo) || detailFrom.equals(rTo)
                            );
                });
    }

    @Override
    public Integer createRental(CreateRentalDetail detail) {
        if (detail.getContractId() == null || detail.getCarId() == null) {
            return null;
        }

        Vozilo v = new Vozilo();
        v.setId(detail.getCarId());

        List<Najam> vehicleRentals = this.najamRepository.findByIdvozilo(v);

        // Check if an active rental already exists or detail date is invalid (between any of rental starts and ends)
        if (!validate(detail, vehicleRentals)) {
            return null;
        }

        Optional<Vozilo> vehicleOpt = this.voziloRepository.findById(detail.getCarId());
        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(detail.getContractId());

        if (vehicleOpt.isEmpty() || contractOpt.isEmpty()) {
            return null;
        }

        Najam rental = new Najam();
        rental.setAktivan(detail.getActive());
        rental.setIdvozilo(vehicleOpt.get());
        rental.setIdugovor(contractOpt.get());
        rental.setVrijemeod(detail.getTimeFrom().toInstant());
        rental.setVrijemedo(detail.getTimeTo().toInstant());

        rental = this.najamRepository.save(rental);

        return rental.getId();
    }
}
