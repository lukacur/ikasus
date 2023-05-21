package hr.fer.ika.ikasus.DTO.incoming;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class UpdateContractDetail {
    private String contractTag;
    private String content;
    private String title;
    private Boolean signed;
    private Date signedOn;
    private Double price;
    private String signatureBase64Encoded;
    private Integer rentalRequestId;

    public UpdateContractDetail() {
    }

    public String getContractTag() {
        return contractTag;
    }

    public void setContractTag(String contractTag) {
        this.contractTag = contractTag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Date getSignedOn() {
        return signedOn;
    }

    public void setSignedOn(Date signedOn) {
        this.signedOn = signedOn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSignatureBase64Encoded() {
        return signatureBase64Encoded;
    }

    public void setSignatureBase64Encoded(String signatureBase64Encoded) {
        this.signatureBase64Encoded = signatureBase64Encoded;
    }

    public Integer getRentalRequestId() {
        return rentalRequestId;
    }

    public void setRentalRequestId(Integer rentalRequestId) {
        this.rentalRequestId = rentalRequestId;
    }
}
