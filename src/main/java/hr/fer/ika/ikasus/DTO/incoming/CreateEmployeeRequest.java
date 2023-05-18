package hr.fer.ika.ikasus.DTO.incoming;

/**
 * @author Luka Ćurić
 */
public class CreateEmployeeRequest {
    private String oib;
    private String name;
    private String surname;
    private Integer idLocation;

    public CreateEmployeeRequest() {
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }
}
