package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.inout.VehicleType;
import hr.fer.ika.ikasus.service.VehicleTypeService;
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
        value = "/api/authenticated/vehicle-types",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class VehicleTypeController {
    private final VehicleTypeService vehicleTypeService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleType>> getVehicleTypes() {
        return ResponseEntity.ok(this.vehicleTypeService.getAllVehicleTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleType> getVehicleType(@PathVariable("id") String id) {
        VehicleType type = this.vehicleTypeService.getVehicleType(id);

        if (type == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(type);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateVehicleType(
            @PathVariable("id") String id,
            @RequestBody VehicleType vehicleType
    ) {
        boolean updated = this.vehicleTypeService.updateVehicleType(id, vehicleType);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createVehicleType(
            HttpServletRequest req,
            @RequestBody VehicleType vehicleType
    ) {
        String createdVehTypeId = this.vehicleTypeService.createVehicleType(vehicleType);

        if (createdVehTypeId == null) {
            CommonResponse errorResp = new CommonResponse();
            errorResp.setIsError(true);
            errorResp.setError("Unable to create resource");

            return ResponseEntity.badRequest().body(errorResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdVehTypeId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteVehicleType(@RequestBody VehicleType vehicleType) {
        boolean deleted = this.vehicleTypeService.deleteVehicleType(vehicleType.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
