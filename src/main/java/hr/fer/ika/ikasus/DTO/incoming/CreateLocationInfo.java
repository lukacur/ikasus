package hr.fer.ika.ikasus.DTO.incoming;

/**
 * @author Luka Ćurić
 */
public class CreateLocationInfo {
    private String address;
    private String zipCode;
    private String city;
    private String country;

    public CreateLocationInfo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
