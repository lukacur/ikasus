package hr.fer.ika.ikasus.DAO.util;

/**
 * @author Luka Ćurić
 */
public class RentalRequestStatus {
    public static final String REQUEST_CONFIRMED = "POTVRDEN";
    public static final String REQUEST_ON_HOLD = "NA CEKANJU";
    public static final String REQUEST_REJECTED = "ODBIJEN";

    private RentalRequestStatus() {}

    public static boolean isValidStatus(String status) {
        return switch (status) {
            case REQUEST_CONFIRMED, REQUEST_ON_HOLD, REQUEST_REJECTED -> true;
            default -> false;
        };
    }
}
