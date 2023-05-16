package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMDInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMaster;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;
import hr.fer.ika.ikasus.repository.VoziloRepository;
import hr.fer.ika.ikasus.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class VehicleServiceImpl implements VehicleService {
    private final VoziloRepository voziloRepository;

    public VehicleServiceImpl(VoziloRepository voziloRepository) {
        this.voziloRepository = voziloRepository;
    }

    @Override
    public List<VehicleMaster> getCars() {
        return this.voziloRepository.findAll().stream()
                .map(v -> {
                    VehicleMaster vehicleMaster = new VehicleMaster();
                    vehicleMaster.setId(v.getId());
                    vehicleMaster.setManufacturer(v.getProizvodjac());
                    vehicleMaster.setRegistration(v.getRegistracija());
                    vehicleMaster.setName(v.getNaziv());
                    vehicleMaster.setPricePerDay(v.getDnevnacijena().doubleValue());

                    return vehicleMaster;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VehicleMDInfo getCarMDInfo(Integer carId) {
        Optional<Vozilo> vehOpt = this.voziloRepository.findById(carId);

        if (vehOpt.isEmpty()) {
            return null;
        }

        Vozilo vehicle = vehOpt.get();

        VehicleMDInfo mdInfo = new VehicleMDInfo();
        mdInfo.setId(vehicle.getId());
        mdInfo.setManufacturer(vehicle.getProizvodjac());
        mdInfo.setName(vehicle.getNaziv());
        mdInfo.setRegistration(vehicle.getRegistracija());
        mdInfo.setPricePerDay(vehicle.getDnevnacijena().doubleValue());
        mdInfo.setRentals(
                vehicle.getNajams().stream()
                        .map(n -> {
                            RentalDetail rentalDetail = new RentalDetail();
                            rentalDetail.setId(n.getId());
                            rentalDetail.setActive(n.getAktivan());

                            if (n.getVrijemeod() != null) {
                                rentalDetail.setTimeFrom(Date.from(n.getVrijemeod()));
                            }

                            if (n.getVrijemedo() != null) {
                                rentalDetail.setTimeTo(Date.from(n.getVrijemedo()));
                            }

                            rentalDetail.setCarId(vehicle.getId());

                            return rentalDetail;
                        })
                        .collect(Collectors.toList())
        );

        return mdInfo;
    }
}
