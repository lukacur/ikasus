package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.AvailableVehicleFilter;
import hr.fer.ika.ikasus.DTO.incoming.CreateVehicleMaster;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.RatingInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMDInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMaster;
import hr.fer.ika.ikasus.service.RatingService;
import hr.fer.ika.ikasus.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/vehicles",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class VehicleController {
    private final VehicleService vehicleService;
    private final RatingService ratingService;

    public VehicleController(VehicleService vehicleService, RatingService ratingService) {
        this.vehicleService = vehicleService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleMaster>> getVehicles() {
        return ResponseEntity.ok(this.vehicleService.getVehicles());
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<RatingInfo>> getVehicleRatings(@PathVariable("id") Integer vehicleId) {
        return ResponseEntity.ok(this.ratingService.getRatingsForVehicle(vehicleId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleMDInfo> getVehicleInfo(@PathVariable("id") Integer vehicleId) {
        if (vehicleId == null) {
            return ResponseEntity.badRequest().build();
        }

        VehicleMDInfo mdInfo = this.vehicleService.getVehicleMDInfo(vehicleId);

        if (mdInfo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mdInfo);
    }

    @PostMapping("/available")
    public ResponseEntity<List<VehicleMaster>> getAvailableVehicles(
            @RequestBody AvailableVehicleFilter availableVehicleFilter
    ) {
        return ResponseEntity.ok(this.vehicleService.getAvailableVehicles(availableVehicleFilter));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateVehicle(
            @PathVariable("id") Integer id,
            @RequestBody CreateVehicleMaster createVehicleMaster
    ) {
        boolean updated = this.vehicleService.updateVehicle(id, createVehicleMaster);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid vehicle update request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createVehicle(
            HttpServletRequest req,
            @RequestBody CreateVehicleMaster createVehicleMaster
    ) {
        Integer createdVehicleId = this.vehicleService.createVehicle(createVehicleMaster);

        if (createdVehicleId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Vehicle couldn't be created");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdVehicleId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteVehicle(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.vehicleService.deleteVehicle(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Unable to delete vehicle");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
