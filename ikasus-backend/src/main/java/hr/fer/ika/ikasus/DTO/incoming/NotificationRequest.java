package hr.fer.ika.ikasus.DTO.incoming;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class NotificationRequest {
    private Integer rentalId;
    private String content;
    private Date time;

    public NotificationRequest() {
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
