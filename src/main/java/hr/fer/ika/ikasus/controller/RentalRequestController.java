package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalRequestInfo;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.RentalRequestMaster;
import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.service.RentalRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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
        value = "/api/authenticated/rental-requests",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class RentalRequestController {
    private final RentalRequestService rentalRequestService;

    public RentalRequestController(RentalRequestService rentalRequestService) {
        this.rentalRequestService = rentalRequestService;
    }

    @GetMapping
    public ResponseEntity<List<RentalRequestMaster>> getRentalRequests(
            Authentication auth
    ) {
        List<RentalRequestMaster> data = null;

        if (Authorities.hasCustomerAuthority(auth)) {
            data = this.rentalRequestService.getRentalRequestsByCustomer(Authorities.extractSubjectId(auth));
        } else if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            data = this.rentalRequestService.getAllRentalRequests();
        }

        if (data != null) {
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalRequestMaster> getRentalRequest(
            Authentication auth,
            @PathVariable("id") Integer rentalRequestId
    ) {
        RentalRequestMaster requestMaster = null;

        if (Authorities.hasCustomerAuthority(auth)) {
            Integer customerId = Authorities.extractSubjectId(auth);

            requestMaster = this.rentalRequestService.getRentalRequest(customerId, rentalRequestId);
        } else if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            requestMaster = this.rentalRequestService.getRentalRequest(rentalRequestId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (requestMaster == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(requestMaster);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createRentalRequest(
            HttpServletRequest req,
            Authentication auth,
            @RequestBody CreateRentalRequestInfo createRentalRequestInfo
    ) {
        if (!Authorities.hasCustomerAuthority(auth) || Authorities.extractSubjectId(auth) == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid user");

            return ResponseEntity.badRequest().body(errResp);
        }

        Integer customerId = Authorities.extractSubjectId(auth);

        Integer createdRentalRequestId = this.rentalRequestService.createRentalRequest(
                customerId, createRentalRequestInfo
        );

        if (createdRentalRequestId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create rental request request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdRentalRequestId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> cancelRentalRequest(
            Authentication auth,
            @RequestBody DeleteRequest<Integer> deleteRequest
    ) {
        boolean deleted;

        Integer rentalId = deleteRequest.getId();
        if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            deleted = this.rentalRequestService.cancelRentalRequest(rentalId);
        } else {
            Integer customerId = Authorities.extractSubjectId(auth);
            deleted = this.rentalRequestService.cancelRentalRequest(customerId, rentalId);
        }

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid cancel rental request request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/parts")
    public ResponseEntity<CommonResponse> cancelRentalRequestPart(
            Authentication auth,
            @RequestBody DeleteRequest<Integer> deleteRentalPartRequest
    ) {
        boolean deleted;

        Integer rentalId = deleteRentalPartRequest.getId();
        if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            deleted = this.rentalRequestService.cancelRentalRequestPart(rentalId);
        } else {
            Integer customerId = Authorities.extractSubjectId(auth);
            deleted = this.rentalRequestService.cancelRentalRequestPart(customerId, rentalId);
        }

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid cancel rental request part request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
