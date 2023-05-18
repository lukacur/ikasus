package hr.fer.ika.ikasus.DTO.incoming;

import hr.fer.ika.ikasus.DTO.inout.VehicleRentalInfo;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public class CreateRentalRequestInfo {
    private List<VehicleRentalInfo> requestedRentals;

    public CreateRentalRequestInfo() {
    }

    public List<VehicleRentalInfo> getRequestedRentals() {
        return requestedRentals;
    }

    public void setRequestedRentals(List<VehicleRentalInfo> requestedRentals) {
        this.requestedRentals = requestedRentals;
    }
}
