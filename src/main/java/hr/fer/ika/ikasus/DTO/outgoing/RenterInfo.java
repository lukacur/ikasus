package hr.fer.ika.ikasus.DTO.outgoing;

/**
 * @author Luka Ćurić
 */
public class RenterInfo {
    private EmployeeInfo employeeInfo;
    private String email;
    private String phoneNumber;

    public RenterInfo() {
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
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
}
