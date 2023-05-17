package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Lokacija;
import hr.fer.ika.ikasus.DAO.TipVozila;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.CreateVehicleMaster;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMDInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMaster;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;
import hr.fer.ika.ikasus.repository.LokacijaRepository;
import hr.fer.ika.ikasus.repository.TipVozilaRepository;
import hr.fer.ika.ikasus.repository.VoziloRepository;
import hr.fer.ika.ikasus.resource.AppImage;
import hr.fer.ika.ikasus.service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
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
    private final TipVozilaRepository tipVozilaRepository;
    private final LokacijaRepository lokacijaRepository;

    public VehicleServiceImpl(
            VoziloRepository voziloRepository,
            TipVozilaRepository tipVozilaRepository,
            LokacijaRepository lokacijaRepository
    ) {
        this.voziloRepository = voziloRepository;
        this.tipVozilaRepository = tipVozilaRepository;
        this.lokacijaRepository = lokacijaRepository;
    }

    @Override
    public boolean updateVehicle(Integer vehicleId, CreateVehicleMaster createVehicleMaster) {
        if (vehicleId == null) {
            return false;
        }

        Optional<Vozilo> vehOpt = this.voziloRepository.findById(vehicleId);

        if (vehOpt.isEmpty()) {
            return false;
        }

        Vozilo vehicle = vehOpt.get();


        if (createVehicleMaster.getImageBase64Encoded() != null) {
            AppImage image = AppImage.fromBase64Builder()
                    .withImageData(createVehicleMaster.getImageBase64Encoded())
                    .build();

            try {
                image.save();
            } catch (IOException e) {
                return false;
            }
        }

        if (createVehicleMaster.getVehicleTypeId() != null) {
            Optional<TipVozila> vtOpt = this.tipVozilaRepository.findById(createVehicleMaster.getVehicleTypeId());
            if (vtOpt.isEmpty()) {
                return false;
            }

            vehicle.setIdtip(vtOpt.get());
        }

        if (createVehicleMaster.getLocationId() != null) {
            Optional<Lokacija> locOpt = this.lokacijaRepository.findById(createVehicleMaster.getLocationId());
            if (locOpt.isEmpty()) {
                return false;
            }

            vehicle.setIdlokacija(locOpt.get());
        }

        if (createVehicleMaster.getManufacturer() != null) {
            vehicle.setProizvodjac(createVehicleMaster.getManufacturer());
        }

        if (createVehicleMaster.getKmDriven() != null) {
            vehicle.setKilometraza(createVehicleMaster.getKmDriven());
        }

        if (createVehicleMaster.getName() != null) {
            vehicle.setNaziv(createVehicleMaster.getName());
        }

        if (createVehicleMaster.getRegistration() != null) {
            vehicle.setRegistracija(createVehicleMaster.getRegistration());
        }

        this.voziloRepository.save(vehicle);

        return true;
    }

    @Override
    @Transactional(rollbackFor = { IllegalStateException.class })
    public Integer createVehicle(CreateVehicleMaster createVehicleMaster) {
        if (createVehicleMaster.getVehicleTypeId() == null) {
            return null;
        }

        Vozilo v = new Vozilo();
        v.setDnevnacijena(BigDecimal.valueOf(createVehicleMaster.getPricePerDay()));
        v.setKilometraza(createVehicleMaster.getKmDriven());
        v.setNaziv(createVehicleMaster.getName());
        v.setProizvodjac(createVehicleMaster.getManufacturer());
        v.setRegistracija(createVehicleMaster.getRegistration());

        Optional<TipVozila> vehTypeOpt = this.tipVozilaRepository.findById(createVehicleMaster.getVehicleTypeId());

        if (vehTypeOpt.isEmpty()) {
            return null;
        }

        TipVozila vehType = vehTypeOpt.get();
        v.setIdtip(vehType);

        if (createVehicleMaster.getLocationId() != null) {
            Optional<Lokacija> locationOpt = this.lokacijaRepository.findById(createVehicleMaster.getLocationId());

            if (locationOpt.isEmpty()) {
                return null;
            }

            Lokacija location = locationOpt.get();
            v.setIdlokacija(location);
        }

        v = this.voziloRepository.save(v);

        Integer vehicleId = v.getId();

        if (createVehicleMaster.getImageBase64Encoded() != null) {
            String imagePath = AppImage.CAR_IMAGE_ROOT + "vehicle_" + v.getIdtip().getNazivtip() + vehicleId + ".jpg";
            v.setPutdoslike(imagePath);

            try {
                AppImage image = AppImage.fromBase64Builder()
                        .withImagePath(imagePath)
                        .withImageData(createVehicleMaster.getImageBase64Encoded())
                        .build();
                image.save();

                this.voziloRepository.save(v);
            } catch (Exception ex) {
                throw new IllegalStateException("Rollback: Image couldn't be set");
            }
        }

        return null;
    }

    @Override
    public List<VehicleMaster> getVehicles() {
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
    public VehicleMDInfo getVehicleMDInfo(Integer carId) {
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

    @Override
    public boolean deleteVehicle(Integer id) {
        if (id == null) {
            return false;
        }

        Vozilo v = new Vozilo();
        v.setId(id);

        this.voziloRepository.delete(v);

        return true;
    }
}
