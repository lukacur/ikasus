package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.outgoing.VehicleMDInfo;
import hr.fer.ika.ikasus.DTO.outgoing.VehicleMaster;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface VehicleService {
    List<VehicleMaster> getCars();
    VehicleMDInfo getCarMDInfo(Integer carId);
}
