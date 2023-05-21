package hr.fer.ika.ikasus.DTO.inout;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class VehicleRentalInfo {
    private Integer vehicleId;
    private Date rentFrom;
    private Date rentTo;

    public VehicleRentalInfo() {
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getRentFrom() {
        return rentFrom;
    }

    public void setRentFrom(Date rentFrom) {
        this.rentFrom = rentFrom;
    }

    public Date getRentTo() {
        return rentTo;
    }

    public void setRentTo(Date rentTo) {
        this.rentTo = rentTo;
    }
}
