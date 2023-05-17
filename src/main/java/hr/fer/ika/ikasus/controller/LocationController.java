package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.outgoing.LocationInfo;
import hr.fer.ika.ikasus.service.LocationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/locations",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationInfo>> getAllLocations() {
        return ResponseEntity.ok(this.locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationInfo> getLocation(@PathVariable("id") Integer locationId) {
        LocationInfo location = this.locationService.getLocation(locationId);

        if (location == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(location);
    }
}
