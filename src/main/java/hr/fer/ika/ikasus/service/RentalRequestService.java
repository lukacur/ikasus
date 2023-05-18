package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateRentalRequestInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RentalRequestMaster;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface RentalRequestService {
    List<RentalRequestMaster> getAllRentalRequests();
    List<RentalRequestMaster> getRentalRequestsByCustomer(Integer customerId);

    RentalRequestMaster getRentalRequest(Integer rentalRequestId);
    RentalRequestMaster getRentalRequest(Integer customerId, Integer rentalRequestId);

    Integer createRentalRequest(Integer customerId, CreateRentalRequestInfo createRentalRequestInfo);

    boolean cancelRentalRequest(Integer rentalRequestId);
    boolean cancelRentalRequest(Integer customerId, Integer rentalRequestId);

    boolean cancelRentalRequestPart(Integer rentalRequestPartId);
    boolean cancelRentalRequestPart(Integer customerId, Integer rentalRequestPartId);
}
