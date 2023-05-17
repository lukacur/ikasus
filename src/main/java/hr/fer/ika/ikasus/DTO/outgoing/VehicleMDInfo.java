package hr.fer.ika.ikasus.DTO.outgoing;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public class VehicleMDInfo {
    private Integer id;
    private String registration;
    private String name;
    private String manufacturer;
    private Integer kmDriven;
    private Double pricePerDay;
    private String imagePath;
    private String vehicleTypeId;
    private Integer locationId;

    private List<RentalDetail> rentals;

    public VehicleMDInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(Integer kmDriven) {
        this.kmDriven = kmDriven;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public List<RentalDetail> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDetail> rentals) {
        this.rentals = rentals;
    }
}
