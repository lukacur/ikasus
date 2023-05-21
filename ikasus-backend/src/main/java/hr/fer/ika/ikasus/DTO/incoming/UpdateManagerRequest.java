package hr.fer.ika.ikasus.DTO.incoming;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class UpdateManagerRequest {
    private CreateEmployeeRequest createEmployeeRequest;
    private String username;
    private String password;
    private Date managerFrom;
    private Date managerTo;

    public UpdateManagerRequest() {
    }

    public CreateEmployeeRequest getCreateEmployeeRequest() {
        return createEmployeeRequest;
    }

    public void setCreateEmployeeRequest(CreateEmployeeRequest createEmployeeRequest) {
        this.createEmployeeRequest = createEmployeeRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getManagerFrom() {
        return managerFrom;
    }

    public void setManagerFrom(Date managerFrom) {
        this.managerFrom = managerFrom;
    }

    public Date getManagerTo() {
        return managerTo;
    }

    public void setManagerTo(Date managerTo) {
        this.managerTo = managerTo;
    }
}
