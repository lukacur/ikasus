package hr.fer.ika.ikasus.DTO.incoming;

/**
 * @author Luka Ćurić
 */
public class CreateVehicleMaster {
    private String registration;
    private String name;
    private String manufacturer;
    private Integer kmDriven;
    private Double pricePerDay;
    private String imageBase64Encoded;
    private Integer locationId;
    private String vehicleTypeId;

    public CreateVehicleMaster() {
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

    public String getImageBase64Encoded() {
        return imageBase64Encoded;
    }

    public void setImageBase64Encoded(String imageBase64Encoded) {
        this.imageBase64Encoded = imageBase64Encoded;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
