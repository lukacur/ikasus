package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;
import hr.fer.ika.ikasus.service.RentalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/rentals",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalDetail>> getAllRentals() {
        return ResponseEntity.ok(this.rentalService.getAllRentalDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDetail> getRental(@PathVariable Integer rentalId) {
        RentalDetail rd = this.rentalService.getRentalDetail(rentalId);

        if (rd == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(rd);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateRental(
            @PathVariable("id") Integer rentalId,
            @RequestBody CreateRentalDetail createRentalDetail
    ) {
        boolean updated = this.rentalService.updateRental(rentalId, createRentalDetail);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update rental request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createRental(
            HttpServletRequest req,
            @RequestBody CreateRentalDetail createRentalDetail
    ) {
        Integer createdRentalId = this.rentalService.createRental(createRentalDetail);

        if (createdRentalId == null) {
            CommonResponse comResp = new CommonResponse();
            comResp.setIsError(true);
            comResp.setError("Rental couldn't be created");

            return ResponseEntity.badRequest().body(comResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdRentalId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteRental(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.rentalService.deleteRental(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete rental request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
