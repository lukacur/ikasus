package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.service.RentalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
}
