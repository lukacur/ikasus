package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.CreateRatingInfo;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.incoming.UpdateRatingInfo;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.RatingInfo;
import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/ratings",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<RatingInfo>> getRatingsByCustomer(Authentication auth) {
        if (!Authorities.hasCustomerAuthority(auth)) {
            return ResponseEntity.badRequest().build();
        }

        Integer customerId = Authorities.extractSubjectId(auth);

        if (customerId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<RatingInfo> rating = this.ratingService.getRatingsByCustomer(customerId);

        if (rating == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingInfo> getRating(
            @PathVariable("id") Integer ratingId
    ) {
        RatingInfo rating = this.ratingService.getRating(ratingId);

        if (rating == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(rating);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateRating(
            Authentication auth,
            @PathVariable("id") Integer ratingId,
            @RequestBody UpdateRatingInfo updateRatingInfo
    ) {
        if (!Authorities.hasCustomerAuthority(auth)) {
            return ResponseEntity.badRequest().build();
        }

        Integer customerId = Authorities.extractSubjectId(auth);

        if (customerId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid user");

            return ResponseEntity.badRequest().body(errResp);
        }

        boolean updated = this.ratingService.updateRating(customerId, ratingId, updateRatingInfo);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update rating request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createRating(
            HttpServletRequest req,
            Authentication auth,
            @RequestBody CreateRatingInfo createRatingInfo
    ) {
        if (!Authorities.hasCustomerAuthority(auth)) {
            return ResponseEntity.badRequest().build();
        }

        Integer customerId = Authorities.extractSubjectId(auth);

        if (customerId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid user");

            return ResponseEntity.badRequest().body(errResp);
        }

        Integer createdRatingId = this.ratingService.createRating(customerId, createRatingInfo);

        if (createdRatingId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create rating request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdRatingId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteRating(
            Authentication auth,
            @RequestBody DeleteRequest<Integer> deleteRequest
    ) {
        Integer customerId = null;

        if (Authorities.hasCustomerAuthority(auth)) {
            customerId = Authorities.extractSubjectId(auth);
        }

        boolean deleted = this.ratingService.deleteRating(customerId, deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete rating request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
