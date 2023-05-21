package hr.fer.ika.ikasus.DTO.incoming;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class SignContractRequest {
    private Integer contractId;
    private String signatureBase64Encoded;
    private Date timeSigned;

    public SignContractRequest() {
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getSignatureBase64Encoded() {
        return signatureBase64Encoded;
    }

    public void setSignatureBase64Encoded(String signatureBase64Encoded) {
        this.signatureBase64Encoded = signatureBase64Encoded;
    }

    public Date getTimeSigned() {
        return timeSigned;
    }

    public void setTimeSigned(Date timeSigned) {
        this.timeSigned = timeSigned;
    }
}
