package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Lokacija;
import hr.fer.ika.ikasus.DTO.incoming.CreateLocationInfo;
import hr.fer.ika.ikasus.DTO.outgoing.LocationInfo;
import hr.fer.ika.ikasus.repository.LokacijaRepository;
import hr.fer.ika.ikasus.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class LocationServiceImpl implements LocationService {
    private final LokacijaRepository lokacijaRepository;

    public LocationServiceImpl(LokacijaRepository lokacijaRepository) {
        this.lokacijaRepository = lokacijaRepository;
    }

    @Override
    public List<LocationInfo> getAllLocations() {
        return this.lokacijaRepository.findAll().stream()
                .map(l -> {
                    LocationInfo locationInfo = new LocationInfo();
                    locationInfo.setId(l.getId());
                    locationInfo.setAddress(l.getAdresa());
                    locationInfo.setZipCode(l.getPbr());
                    locationInfo.setCity(l.getGrad());
                    locationInfo.setCountry(l.getDrzava());

                    return locationInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public LocationInfo getLocation(Integer locationId) {
        if (locationId == null) {
            return null;
        }

        Optional<Lokacija> locOpt = this.lokacijaRepository.findById(locationId);

        if (locOpt.isEmpty()) {
            return null;
        }

        return locOpt.map(l -> {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setId(l.getId());
            locationInfo.setAddress(l.getAdresa());
            locationInfo.setZipCode(l.getPbr());
            locationInfo.setCity(l.getGrad());
            locationInfo.setCountry(l.getDrzava());

            return locationInfo;
        }).get();
    }

    @Override
    public boolean updateLocation(Integer locationId, CreateLocationInfo createLocationInfo) {
        if (locationId == null) {
            return false;
        }

        Optional<Lokacija> locOpt = this.lokacijaRepository.findById(locationId);

        if (locOpt.isEmpty()) {
            return false;
        }

        Lokacija location = locOpt.get();
        if (createLocationInfo.getAddress() != null) {
            location.setAdresa(createLocationInfo.getAddress());
        }

        if (createLocationInfo.getZipCode() != null) {
            location.setPbr(createLocationInfo.getZipCode());
        }

        if (createLocationInfo.getCity() != null) {
            location.setGrad(createLocationInfo.getCity());
        }

        if (createLocationInfo.getCountry() != null) {
            location.setDrzava(createLocationInfo.getCountry());
        }

        this.lokacijaRepository.save(location);

        return true;
    }

    @Override
    public Integer createLocation(CreateLocationInfo createLocationInfo) {
        Lokacija location = new Lokacija();
        location.setAdresa(createLocationInfo.getAddress());
        location.setPbr(createLocationInfo.getZipCode());
        location.setGrad(createLocationInfo.getCity());
        location.setDrzava(createLocationInfo.getCountry());

        location = this.lokacijaRepository.save(location);

        return location.getId();
    }

    @Override
    public boolean deleteLocation(Integer locationId) {
        if (locationId == null) {
            return false;
        }

        Optional<Lokacija> locOpt = this.lokacijaRepository.findById(locationId);

        if (locOpt.isEmpty()) {
            return false;
        }

        this.lokacijaRepository.delete(locOpt.get());

        return true;
    }
}
