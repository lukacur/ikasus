package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateRatingInfo;
import hr.fer.ika.ikasus.DTO.incoming.UpdateRatingInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RatingInfo;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface RatingService {
    List<RatingInfo> getAllRatings();
    List<RatingInfo> getRatingsForVehicle(Integer vehicleId);
    List<RatingInfo> getRatingsByCustomer(Integer customerId);
    RatingInfo getRating(Integer ratingId);

    boolean updateRating(Integer customerId, Integer ratingId, UpdateRatingInfo updateRatingInfo);
    Integer createRating(Integer customerId, CreateRatingInfo createRatingInfo);
    boolean deleteRating(Integer customerId, Integer ratingId);
}
