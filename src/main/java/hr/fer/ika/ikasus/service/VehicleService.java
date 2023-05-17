package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateVehicleMaster;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMDInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMaster;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface VehicleService {
    List<VehicleMaster> getVehicles();
    VehicleMDInfo getVehicleMDInfo(Integer vehicleId);
    boolean updateVehicle(Integer vehicleId, CreateVehicleMaster createVehicleMaster);
    Integer createVehicle(CreateVehicleMaster createVehicleMaster);
    boolean deleteVehicle(Integer id);
}
