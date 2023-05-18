package hr.fer.ika.ikasus.DTO.outgoing;

import java.util.Date;
import java.util.List;

/**
 * @author Luka Ćurić
 */
public class RentalRequestMaster {
    private Integer id;
    private List<RentalRequestVehicleDetail> vehicleDetails;
    private String status;
    private Date timeCreated;
    private Boolean processed;
    private Date timeProcessed;
    private Integer employeeProcessedId;
    private Integer customerRequestedId;

    public RentalRequestMaster() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<RentalRequestVehicleDetail> getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(List<RentalRequestVehicleDetail> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Date getTimeProcessed() {
        return timeProcessed;
    }

    public void setTimeProcessed(Date timeProcessed) {
        this.timeProcessed = timeProcessed;
    }

    public Integer getEmployeeProcessedId() {
        return employeeProcessedId;
    }

    public void setEmployeeProcessedId(Integer employeeProcessedId) {
        this.employeeProcessedId = employeeProcessedId;
    }

    public Integer getCustomerRequestedId() {
        return customerRequestedId;
    }

    public void setCustomerRequestedId(Integer customerRequestedId) {
        this.customerRequestedId = customerRequestedId;
    }
}
