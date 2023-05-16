package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;

/**
 * @author Luka Ćurić
 */
public interface RentalService {
    Integer createRental(CreateRentalDetail detail);
}
