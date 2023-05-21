package hr.fer.ika.ikasus.DTO.outgoing;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class ManagerInfo {
    private EmployeeInfo employeeInfo;
    private String username;
    private Date managerFrom;
    private Date managerTo;

    public ManagerInfo() {
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
