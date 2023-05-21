package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateLocationInfo;
import hr.fer.ika.ikasus.DTO.outgoing.LocationInfo;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface LocationService {
    List<LocationInfo> getAllLocations();
    LocationInfo getLocation(Integer locationId);
    boolean updateLocation(Integer locationId, CreateLocationInfo createLocationInfo);
    Integer createLocation(CreateLocationInfo createLocationInfo);
    boolean deleteLocation(Integer locationId);
}
