package hr.fer.ika.ikasus.controller.manager;

import hr.fer.ika.ikasus.DTO.incoming.CreateLocationInfo;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/manager/locations",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class LocationManagementController {
    private final LocationService locationService;

    public LocationManagementController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateLocation(
            @PathVariable("id") Integer locationId,
            @RequestBody CreateLocationInfo createLocationInfo
    ) {
        boolean updated = this.locationService.updateLocation(locationId, createLocationInfo);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update location request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createLocation(
            HttpServletRequest req,
            @RequestBody CreateLocationInfo createLocationInfo
    ) {
        Integer createdLocationId = this.locationService.createLocation(createLocationInfo);

        if (createdLocationId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create location request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdLocationId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteLocation(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.locationService.deleteLocation(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete location request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
