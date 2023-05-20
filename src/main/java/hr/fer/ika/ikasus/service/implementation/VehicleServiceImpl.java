package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Lokacija;
import hr.fer.ika.ikasus.DAO.TipVozila;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.AvailableVehicleFilter;
import hr.fer.ika.ikasus.DTO.incoming.CreateVehicleMaster;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class VehicleServiceImpl implements VehicleService {
    private final VoziloRepository voziloRepository;
    private final TipVozilaRepository tipVozilaRepository;
    private final LokacijaRepository lokacijaRepository;

    private final Function<Vozilo, VehicleMaster> vehicleMasterMapper = v -> {
        VehicleMaster vehicleMaster = new VehicleMaster();
        vehicleMaster.setId(v.getId());
        vehicleMaster.setManufacturer(v.getProizvodjac());
        vehicleMaster.setKmDriven(v.getKilometraza());
        if (v.getPutdoslike() != null) {
            vehicleMaster.setImagePath(v.getPutdoslike().replace(AppImage.STATIC_CONTENT_PREFIX, ""));
        }
        vehicleMaster.setRegistration(v.getRegistracija());
        vehicleMaster.setName(v.getNaziv());
        vehicleMaster.setPricePerDay(v.getDnevnacijena().doubleValue());
        vehicleMaster.setVehicleTypeId(v.getIdtip().getId());
        if (v.getIdlokacija() != null) {
            vehicleMaster.setLocationId(v.getIdlokacija().getId());
        }

        return vehicleMaster;
    };

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
        if (vehicleId == null ||
                createVehicleMaster.getImageUrl() != null && createVehicleMaster.getImageBase64Encoded() != null
        ) {
            return false;
        }

        Optional<Vozilo> vehOpt = this.voziloRepository.findById(vehicleId);

        if (vehOpt.isEmpty()) {
            return false;
        }

        Vozilo v = vehOpt.get();

        if (createVehicleMaster.getImageBase64Encoded() != null) {
            String imagePath = AppImage.VEHICLE_IMAGE_ROOT + "vehicle_" + v.getIdtip().getNazivtip() + vehicleId + ".jpeg";

            AppImage image = AppImage.fromBase64Builder()
                    .withImagePath(imagePath)
                    .withImageData(createVehicleMaster.getImageBase64Encoded())
                    .build();

            try {
                image.save();
                image.close();
                v.setPutdoslike(imagePath);
            } catch (IOException e) {
                return false;
            }
        } else if (createVehicleMaster.getImageUrl() != null) {
            v.setPutdoslike(createVehicleMaster.getImageUrl());
        }

        if (createVehicleMaster.getVehicleTypeId() != null) {
            Optional<TipVozila> vtOpt = this.tipVozilaRepository.findById(createVehicleMaster.getVehicleTypeId());
            if (vtOpt.isEmpty()) {
                return false;
            }

            v.setIdtip(vtOpt.get());
        }

        if (createVehicleMaster.getLocationId() != null) {
            Optional<Lokacija> locOpt = this.lokacijaRepository.findById(createVehicleMaster.getLocationId());
            if (locOpt.isEmpty()) {
                return false;
            }

            v.setIdlokacija(locOpt.get());
        }

        if (createVehicleMaster.getManufacturer() != null) {
            v.setProizvodjac(createVehicleMaster.getManufacturer());
        }

        if (createVehicleMaster.getKmDriven() != null) {
            v.setKilometraza(createVehicleMaster.getKmDriven());
        }

        if (createVehicleMaster.getName() != null) {
            v.setNaziv(createVehicleMaster.getName());
        }

        if (createVehicleMaster.getRegistration() != null) {
            v.setRegistracija(createVehicleMaster.getRegistration());
        }

        this.voziloRepository.save(v);

        return true;
    }

    @Override
    @Transactional(rollbackFor = { IllegalStateException.class })
    public Integer createVehicle(CreateVehicleMaster createVehicleMaster) {
        if (createVehicleMaster.getVehicleTypeId() == null ||
                createVehicleMaster.getImageUrl() != null && createVehicleMaster.getImageBase64Encoded() != null
        ) {
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
            String imagePath = AppImage.VEHICLE_IMAGE_ROOT + "vehicle_" + v.getIdtip().getNazivtip() + vehicleId + ".jpeg";
            v.setPutdoslike(imagePath);

            try {
                AppImage image = AppImage.fromBase64Builder()
                        .withImagePath(imagePath)
                        .withImageData(createVehicleMaster.getImageBase64Encoded())
                        .build();
                image.save();
                image.close();
            } catch (Exception ex) {
                throw new IllegalStateException("Rollback: Image couldn't be set");
            }
        } else if (createVehicleMaster.getImageUrl() != null) {
            v.setPutdoslike(createVehicleMaster.getImageUrl());
        }

        this.voziloRepository.save(v);

        return vehicleId;
    }

    @Override
    public List<VehicleMaster> getVehicles() {
        return this.voziloRepository.findAll().stream()
                .map(this.vehicleMasterMapper)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleMDInfo getVehicleMDInfo(Integer vehicleId) {
        Optional<Vozilo> vehOpt = this.voziloRepository.findById(vehicleId);

        if (vehOpt.isEmpty()) {
            return null;
        }

        Vozilo vehicle = vehOpt.get();

        VehicleMDInfo mdInfo = new VehicleMDInfo();
        mdInfo.setId(vehicle.getId());
        if (vehicle.getPutdoslike() != null) {
            mdInfo.setImagePath(vehicle.getPutdoslike().replace(AppImage.STATIC_CONTENT_PREFIX, ""));
        }
        mdInfo.setManufacturer(vehicle.getProizvodjac());
        mdInfo.setKmDriven(vehicle.getKilometraza());
        mdInfo.setName(vehicle.getNaziv());
        mdInfo.setRegistration(vehicle.getRegistracija());
        mdInfo.setPricePerDay(vehicle.getDnevnacijena().doubleValue());
        mdInfo.setVehicleTypeId(vehicle.getIdtip().getId());
        if (vehicle.getIdlokacija() != null) {
            mdInfo.setLocationId(vehicle.getIdlokacija().getId());
        }
        mdInfo.setRentals(
                vehicle.getNajams().stream()
                        .map(n -> {
                            RentalDetail rentalDetail = new RentalDetail();
                            rentalDetail.setId(n.getId());
                            rentalDetail.setActive(n.getAktivan());
                            rentalDetail.setKmDriven(n.getPrijedeno());

                            if (n.getIdugovor() != null) {
                                rentalDetail.setContractId(n.getIdugovor().getId());
                            }

                            if (n.getVrijemeod() != null) {
                                rentalDetail.setTimeFrom(Date.from(n.getVrijemeod()));
                            }

                            if (n.getVrijemedo() != null) {
                                rentalDetail.setTimeTo(Date.from(n.getVrijemedo()));
                            }

                            rentalDetail.setVehicleId(vehicle.getId());

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

        Optional<Vozilo> v = this.voziloRepository.findById(id);

        if (v.isEmpty()) {
            return false;
        }

        this.voziloRepository.delete(v.get());

        return true;
    }

    @Override
    public List<VehicleMaster> getAvailableVehicles(AvailableVehicleFilter availableVehicleFilter) {
        List<Vozilo> availableVehicles = this.voziloRepository.getAvailable(
                availableVehicleFilter.getFrom().toInstant(),
                availableVehicleFilter.getTo().toInstant()
        );

        return availableVehicles.stream()
                .filter(v -> availableVehicleFilter.getMaxPricePerDay() == null || v.getDnevnacijena() == null ||
                        v.getDnevnacijena().doubleValue() <= availableVehicleFilter.getMaxPricePerDay()
                )
                .map(this.vehicleMasterMapper)
                .collect(Collectors.toList());
    }
}
