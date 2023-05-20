package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Najam;
import hr.fer.ika.ikasus.DAO.Ugovor;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;
import hr.fer.ika.ikasus.repository.NajamRepository;
import hr.fer.ika.ikasus.repository.UgovorRepository;
import hr.fer.ika.ikasus.repository.VoziloRepository;
import hr.fer.ika.ikasus.service.RentalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<RentalDetail> getAllRentalDetails() {
        return this.najamRepository.findAll().stream()
                .map(r -> {
                    RentalDetail rentalDetail = new RentalDetail();
                    rentalDetail.setId(r.getId());
                    rentalDetail.setTimeFrom(Date.from(r.getVrijemeod()));
                    if (r.getVrijemedo() != null) {
                        rentalDetail.setTimeTo(Date.from(r.getVrijemedo()));
                    }
                    rentalDetail.setKmDriven(r.getPrijedeno());
                    rentalDetail.setActive(r.getAktivan());
                    rentalDetail.setVehicleId(r.getIdvozilo().getId());
                    rentalDetail.setContractId(r.getIdugovor().getId());

                    return rentalDetail;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RentalDetail getRentalDetail(Integer rentalId) {
        if (rentalId == null) {
            return null;
        }

        Optional<Najam> rentalOpt = this.najamRepository.findById(rentalId);

        if (rentalOpt.isEmpty()) {
            return null;
        }

        return rentalOpt.map(r -> {
            RentalDetail rentalDetail = new RentalDetail();
            rentalDetail.setId(r.getId());
            rentalDetail.setTimeFrom(Date.from(r.getVrijemeod()));
            if (r.getVrijemedo() != null) {
                rentalDetail.setTimeTo(Date.from(r.getVrijemedo()));
            }
            rentalDetail.setKmDriven(r.getPrijedeno());
            rentalDetail.setActive(r.getAktivan());
            rentalDetail.setVehicleId(r.getIdvozilo().getId());
            rentalDetail.setContractId(r.getIdugovor().getId());

            return rentalDetail;
        }).get();
    }

    private static boolean validate(CreateRentalDetail detail, List<Najam> vehicleRentals, boolean updateCheck) {
        if (detail.getTimeFrom() == null || detail.getTimeTo() == null ||
                !updateCheck && (detail.getVehicleId() == null || detail.getContractId() == null)
        ) {
            return false;
        }

        Date detailFrom = detail.getTimeFrom();
        Date detailTo = detail.getTimeTo();

        if (detailTo.before(detailFrom)) {
            return false;
        }

        return /*vehicleRentals.stream().noneMatch(r -> r.getAktivan() && detail.getActive()) &&*/
                vehicleRentals.stream().noneMatch(r -> {
                    Date rFrom = Date.from(r.getVrijemeod());
                    Date rTo;

                    if (r.getVrijemedo() == null) {
                        return detailTo.after(rFrom);
                    }

                    rTo = Date.from(r.getVrijemedo());

                    return !(
                            detailTo.before(rFrom) || detailTo.equals(rFrom) ||
                                    detailFrom.after(rTo) || detailFrom.equals(rTo)
                            );
                });
    }

    @Override
    @Transactional(rollbackFor = { IllegalStateException.class })
    public boolean updateRental(Integer rentalId, CreateRentalDetail detail) {
        if (rentalId == null) {
            return false;
        }

        Optional<Najam> rentalOpt = this.najamRepository.findById(rentalId);

        if (rentalOpt.isEmpty()) {
            return false;
        }

        Najam rental = rentalOpt.get();

        Vozilo v = rental.getIdvozilo();

        List<Najam> rentals = this.najamRepository.findByIdvozilo(v).stream()
                .filter(r -> !Objects.equals(r.getId(), rentalId))
                .collect(Collectors.toList());

        if (!validate(detail, rentals, true)) {
            return false;
        }

        rentals = null;

        if (detail.getTimeFrom() != null) {
            rental.setVrijemeod(detail.getTimeFrom().toInstant());
        }

        if (detail.getTimeTo() != null) {
            rental.setVrijemedo(detail.getTimeTo().toInstant());
        }

        if (detail.getKmDriven() != null) {
            rental.setPrijedeno(detail.getKmDriven());
        }

        if (detail.getActive() != null) {
            rental.setAktivan(detail.getActive());
        }

        if (detail.getVehicleId() != null) {
            Optional<Vozilo> vehOpt = this.voziloRepository.findById(detail.getVehicleId());

            if (vehOpt.isEmpty()) {
                throw new IllegalStateException("Rollback: can't set vehicle");
            }

            rental.setIdvozilo(vehOpt.get());
        }

        if (detail.getContractId() != null) {
            Optional<Ugovor> contractOpt = this.ugovorRepository.findById(detail.getContractId());

            if (contractOpt.isEmpty()) {
                throw new IllegalStateException("Rollback: can't set contract");
            }

            rental.setIdugovor(contractOpt.get());
        }

        this.najamRepository.save(rental);

        return true;
    }

    @Override
    public Integer createRental(CreateRentalDetail detail) {
        if (detail.getContractId() == null || detail.getVehicleId() == null) {
            return null;
        }

        Vozilo v = new Vozilo();
        v.setId(detail.getVehicleId());

        List<Najam> vehicleRentals = this.najamRepository.findByIdvozilo(v);

        // Check if an active rental already exists or detail date is invalid (between any of rental starts and ends)
        if (!validate(detail, vehicleRentals, false)) {
            return null;
        }

        Optional<Vozilo> vehicleOpt = this.voziloRepository.findById(detail.getVehicleId());
        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(detail.getContractId());

        if (vehicleOpt.isEmpty() || contractOpt.isEmpty()) {
            return null;
        }

        Najam rental = new Najam();
        rental.setAktivan(detail.getActive());
        rental.setIdvozilo(vehicleOpt.get());
        rental.setIdugovor(contractOpt.get());
        rental.setVrijemeod(detail.getTimeFrom().toInstant());
        if (detail.getTimeTo() != null) {
            rental.setVrijemedo(detail.getTimeTo().toInstant());
        }

        rental = this.najamRepository.save(rental);

        return rental.getId();
    }

    @Override
    public boolean deleteRental(Integer rentalId) {
        if (rentalId == null) {
            return false;
        }

        Optional<Najam> rentalOpt = this.najamRepository.findById(rentalId);

        if (rentalOpt.isEmpty()) {
            return false;
        }

        this.najamRepository.delete(rentalOpt.get());

        return true;
    }
}
