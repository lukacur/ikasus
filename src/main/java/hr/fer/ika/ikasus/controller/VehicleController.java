package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.outgoing.VehicleMDInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMaster;
import hr.fer.ika.ikasus.service.VehicleService;
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
        value = "/api/authenticated/vehicles",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleMaster>> getCars() {
        List<VehicleMaster> cars = this.vehicleService.getCars();

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleMDInfo> getCarInfo(@PathVariable("id") Integer carId) {
        if (carId == null) {
            return ResponseEntity.badRequest().build();
        }

        VehicleMDInfo mdInfo = this.vehicleService.getCarMDInfo(carId);

        if (mdInfo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mdInfo);
    }
}
