package hr.fer.ika.ikasus.DTO.incoming;

/**
 * @author Luka Ćurić
 */
public class UpdateRenterRequest {
    private CreateEmployeeRequest createEmployeeRequest;
    private String email;
    private String phoneNumber;
    private String password;

    public UpdateRenterRequest() {
    }

    public CreateEmployeeRequest getCreateEmployeeRequest() {
        return createEmployeeRequest;
    }

    public void setCreateEmployeeRequest(CreateEmployeeRequest createEmployeeRequest) {
        this.createEmployeeRequest = createEmployeeRequest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
