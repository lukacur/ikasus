package hr.fer.ika.ikasus.DTO.incoming;

import java.util.Date;

/**
 * @author Luka Ćurić
 */
public class AvailableVehicleFilter {
    private Date from;
    private Date to;
    private Double maxPricePerDay;

    public AvailableVehicleFilter() {
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Double getMaxPricePerDay() {
        return maxPricePerDay;
    }

    public void setMaxPricePerDay(Double maxPricePerDay) {
        this.maxPricePerDay = maxPricePerDay;
    }
}
