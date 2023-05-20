package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.*;
import hr.fer.ika.ikasus.DAO.util.RentalRequestStatus;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalRequestInfo;
import hr.fer.ika.ikasus.DTO.inout.VehicleRentalInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RentalRequestMaster;
import hr.fer.ika.ikasus.DTO.outgoing.RentalRequestVehicleDetail;
import hr.fer.ika.ikasus.repository.*;
import hr.fer.ika.ikasus.service.RentalRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class RentalRequestServiceImpl implements RentalRequestService {
    private final ZahtjevZaNajmomRepository zahtjevZaNajmomRepository;
    private final PotrazujeRepository potrazujeRepository;
    private final UnajmiteljRepository unajmiteljRepository;
    private final VoziloRepository voziloRepository;

    private final Function<ZahtjevZaNajmom, RentalRequestMaster> mapper = (rr) -> {
        RentalRequestMaster requestMaster = new RentalRequestMaster();
        requestMaster.setId(rr.getId());
        requestMaster.setProcessed(rr.getObraden());
        requestMaster.setStatus(rr.getStatus());
        requestMaster.setTimeCreated(Date.from(rr.getVrijemezahtjeva()));

        Unajmitelj customer = rr.getIdunajmitelj();
        requestMaster.setCustomerRequestedId(customer.getId());
        requestMaster.setCustomerFullName(customer.getIme() + " " + customer.getPrezime());
        if (rr.getIdiznajmljivac() != null) {
            requestMaster.setEmployeeProcessedId(rr.getIdiznajmljivac().getId());
        }
        if (rr.getVrijemeobrada() != null) {
            requestMaster.setTimeProcessed(Date.from(rr.getVrijemeobrada()));
        }

        if (rr.getPotrazujes() != null) {
            requestMaster.setVehicleDetails(
                    rr.getPotrazujes().stream()
                            .map(p -> {
                                RentalRequestVehicleDetail vri = new RentalRequestVehicleDetail();
                                Vozilo v = p.getIdvozilo();

                                vri.setId(p.getId());
                                vri.setVehicleId(v.getId());
                                vri.setVehicleName(v.getNaziv());
                                vri.setRentFrom(
                                        Date.from(p.getPotraznjaod().atStartOfDay(ZoneId.systemDefault()).toInstant())
                                );
                                vri.setRentTo(
                                        Date.from(p.getPotraznjado().atStartOfDay(ZoneId.systemDefault()).toInstant())
                                );

                                return vri;
                            })
                            .collect(Collectors.toList())
            );
        }

        return requestMaster;
    };

    public RentalRequestServiceImpl(
            ZahtjevZaNajmomRepository zahtjevZaNajmomRepository,
            PotrazujeRepository potrazujeRepository,
            UnajmiteljRepository unajmiteljRepository,
            VoziloRepository voziloRepository
    ) {
        this.zahtjevZaNajmomRepository = zahtjevZaNajmomRepository;
        this.potrazujeRepository = potrazujeRepository;
        this.unajmiteljRepository = unajmiteljRepository;
        this.voziloRepository = voziloRepository;
    }

    @Override
    public List<RentalRequestMaster> getAllRentalRequests() {
        return this.zahtjevZaNajmomRepository.findAll().stream()
                .map(this.mapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalRequestMaster> getRentalRequestsByCustomer(Integer customerId) {
        if (customerId == null) {
            return null;
        }

        return this.zahtjevZaNajmomRepository.findAll().stream()
                .filter(rr -> rr.getIdunajmitelj() != null && Objects.equals(rr.getIdunajmitelj().getId(), customerId))
                .map(this.mapper)
                .collect(Collectors.toList());
    }

    @Override
    public RentalRequestMaster getRentalRequest(Integer rentalRequestId) {
        if (rentalRequestId == null) {
            return null;
        }

        Optional<RentalRequestMaster> requestMaster = this.zahtjevZaNajmomRepository.findById(rentalRequestId)
                .map(this.mapper);

        if (requestMaster.isEmpty()) {
            return null;
        }

        return requestMaster.get();
    }

    @Override
    public RentalRequestMaster getRentalRequest(Integer customerId, Integer rentalRequestId) {
        if (rentalRequestId == null) {
            return null;
        }

        Optional<RentalRequestMaster> requestMaster = this.zahtjevZaNajmomRepository.findById(rentalRequestId)
                .filter(rr -> rr.getIdunajmitelj() != null && Objects.equals(rr.getIdunajmitelj().getId(), customerId))
                .map(this.mapper);

        if (requestMaster.isEmpty()) {
            return null;
        }

        return requestMaster.get();
    }

    @Override
    @Transactional(rollbackFor = { IllegalStateException.class })
    public Integer createRentalRequest(Integer customerId, CreateRentalRequestInfo createRentalRequestInfo) {
        if (customerId == null || createRentalRequestInfo == null ||
                createRentalRequestInfo.getRequestedRentals().isEmpty()
        ) {
            return null;
        }

        Optional<Unajmitelj> renteeOpt = this.unajmiteljRepository.findById(customerId);

        if (renteeOpt.isEmpty()) {
            return null;
        }

        Unajmitelj rentee = renteeOpt.get();

        ZahtjevZaNajmom rentalRequest = new ZahtjevZaNajmom();
        rentalRequest.setStatus(RentalRequestStatus.REQUEST_ON_HOLD);
        rentalRequest.setVrijemezahtjeva(new Date().toInstant());
        rentalRequest.setObraden(false);
        rentalRequest.setIdunajmitelj(rentee);

        rentalRequest = this.zahtjevZaNajmomRepository.save(rentalRequest);

        List<Potrazuje> vehicleSeekList = new LinkedList<>();

        for (VehicleRentalInfo vehicleRentalInfo : createRentalRequestInfo.getRequestedRentals()) {
            Potrazuje p = new Potrazuje();
            p.setIdzahtjev(rentalRequest);

            if (vehicleRentalInfo.getVehicleId() == null) {
                throw new IllegalStateException("Rollback: vehicle couldn't be bound");
            }

            Optional<Vozilo> vehOpt = this.voziloRepository.findById(vehicleRentalInfo.getVehicleId());

            if (vehOpt.isEmpty() || vehicleRentalInfo.getRentFrom() == null || vehicleRentalInfo.getRentTo() == null) {
                throw new IllegalStateException("Rollback: vehicle couldn't be bound");
            }

            Vozilo vehicle = vehOpt.get();

            if (
                    !this.voziloRepository.isAvailable(
                            vehicle.getId(),
                            vehicleRentalInfo.getRentFrom().toInstant(),
                            vehicleRentalInfo.getRentTo().toInstant()
                    )
            ) {
                throw new IllegalStateException("Rollback: vehicle isn't available");
            }

            p.setIdvozilo(vehOpt.get());
            p.setPotraznjaod(vehicleRentalInfo.getRentFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            p.setPotraznjado(vehicleRentalInfo.getRentTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            vehicleSeekList.add(p);
        }

        this.potrazujeRepository.saveAll(vehicleSeekList);

        return rentalRequest.getId();
    }

    @Override
    public boolean cancelRentalRequest(Integer rentalRequestId) {
        if (rentalRequestId == null) {
            return false;
        }

        Optional<ZahtjevZaNajmom> rentReqOpt = this.zahtjevZaNajmomRepository.findById(rentalRequestId);

        if (rentReqOpt.isEmpty()) {
            return false;
        }

        ZahtjevZaNajmom rentalRequest = rentReqOpt.get();

        this.potrazujeRepository.deleteAll(rentalRequest.getPotrazujes());
        this.zahtjevZaNajmomRepository.delete(rentalRequest);

        return true;
    }

    @Override
    public boolean cancelRentalRequest(Integer customerId, Integer rentalRequestId) {
        if (rentalRequestId == null) {
            return false;
        }

        Optional<ZahtjevZaNajmom> rentReqOpt = this.zahtjevZaNajmomRepository.findById(rentalRequestId)
                .filter(rr -> rr.getIdunajmitelj() != null && Objects.equals(rr.getIdunajmitelj().getId(), customerId));

        if (rentReqOpt.isEmpty()) {
            return false;
        }

        ZahtjevZaNajmom rentalRequest = rentReqOpt.get();

        this.potrazujeRepository.deleteAll(rentalRequest.getPotrazujes());
        this.zahtjevZaNajmomRepository.delete(rentalRequest);

        return true;
    }

    @Override
    public boolean cancelRentalRequestPart(Integer rentalRequestPartId) {
        if (rentalRequestPartId == null) {
            return false;
        }

        Optional<Potrazuje> rentReqPartOpt = this.potrazujeRepository.findById(rentalRequestPartId);

        if (rentReqPartOpt.isEmpty()) {
            return false;
        }

        Potrazuje rentReqPart = rentReqPartOpt.get();
        ZahtjevZaNajmom rentalRequest = rentReqPart.getIdzahtjev();
        int size = rentalRequest.getPotrazujes().size();

        this.potrazujeRepository.delete(rentReqPartOpt.get());

        if (size == 1) {
            this.cancelRentalRequest(rentalRequest.getId());
        }

        return true;
    }

    @Override
    public boolean cancelRentalRequestPart(Integer customerId, Integer rentalRequestPartId) {
        if (customerId == null || rentalRequestPartId == null) {
            return false;
        }

        Optional<Potrazuje> rentReqPartOpt = this.potrazujeRepository.findById(rentalRequestPartId)
                .filter(rrp -> {
                    ZahtjevZaNajmom rr = rrp.getIdzahtjev();
                    if (rr == null) {
                        return false;
                    }

                    Unajmitelj rentee = rrp.getIdzahtjev().getIdunajmitelj();

                    if (rentee == null) {
                        return false;
                    }

                    return Objects.equals(rentee.getId(), customerId);
                });

        if (rentReqPartOpt.isEmpty()) {
            return false;
        }
        Potrazuje rentReqPart = rentReqPartOpt.get();
        ZahtjevZaNajmom rentalRequest = rentReqPart.getIdzahtjev();
        int size = rentalRequest.getPotrazujes().size();

        this.potrazujeRepository.delete(rentReqPartOpt.get());

        if (size == 1) {
            this.cancelRentalRequest(rentalRequest.getId());
        }

        return true;
    }
}
