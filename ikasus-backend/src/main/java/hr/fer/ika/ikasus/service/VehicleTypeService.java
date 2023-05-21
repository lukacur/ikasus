package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.inout.VehicleType;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface VehicleTypeService {
    List<VehicleType> getAllVehicleTypes();
    VehicleType getVehicleType(String id);

    boolean updateVehicleType(String id, VehicleType vehicleType);
    String createVehicleType(VehicleType vehicleType);

    boolean deleteVehicleType(String id);
}
