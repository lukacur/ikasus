package hr.fer.ika.ikasus.DTO.outgoing;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class ContractDetail {
    private String contractTag;
    private String content;
    private String title;
    private Boolean signed;
    private Date signedOn;
    private String signaturePath;
    private double price;
    private Integer rentalRequestId;

    public ContractDetail() {
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

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getRentalRequestId() {
        return rentalRequestId;
    }

    public void setRentalRequestId(Integer rentalRequestId) {
        this.rentalRequestId = rentalRequestId;
    }
}
