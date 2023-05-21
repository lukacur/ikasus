package hr.fer.ika.ikasus.DTO.incoming;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class ContractIssueRequest {
    private Integer customerId;
    private Integer rentalRequestId;
    private Date dateOfIssue;
    private UpdateContractDetail contractInfo;

    public ContractIssueRequest() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRentalRequestId() {
        return rentalRequestId;
    }

    public void setRentalRequestId(Integer rentalRequestId) {
        this.rentalRequestId = rentalRequestId;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public UpdateContractDetail getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(UpdateContractDetail contractInfo) {
        this.contractInfo = contractInfo;
    }
}
