package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface RentalService {
    List<RentalDetail> getAllRentalDetails();
    RentalDetail getRentalDetail(Integer rentalId);
    boolean updateRental(Integer rentalId, CreateRentalDetail detail);
    Integer createRental(CreateRentalDetail detail);
    boolean deleteRental(Integer rentalId);
}
