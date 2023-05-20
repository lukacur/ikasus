package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.incoming.SignContractRequest;
import hr.fer.ika.ikasus.DTO.incoming.UpdateContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.ContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;
import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.service.ContractService;
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
        value = "/api/authenticated/contracts",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class ContractController {
    public static final String ROOT_PATH = "/api/authenticated/contracts";

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<List<ContractMaster>> getContracts(Authentication auth) {
        List<ContractMaster> contractMasters = null;

        if (Authorities.hasCustomerAuthority(auth)) {
            Integer customerId = Authorities.extractSubjectId(auth);
            contractMasters = this.contractService.getContractMastersForCustomer(customerId);
        } else if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            contractMasters = this.contractService.getContractMasters();
        }

        if (contractMasters == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(contractMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDetail> getContractDetail(
            Authentication auth,
            @PathVariable("id") Integer contractId
    ) {
        ContractDetail detail = null;

        if (Authorities.hasCustomerAuthority(auth)) {
            Integer customerId = Authorities.extractSubjectId(auth);
            detail = this.contractService.getDetailsFor(customerId, contractId);
        } else if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            detail = this.contractService.getDetailsFor(contractId);
        }

        if (detail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(detail);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateContractDetails(
            Authentication auth,
            @PathVariable("id") Integer contractId,
            @RequestBody UpdateContractDetail contractDetailUpdate
    ) {
        if (!Authorities.hasManagerOrEmployeeAuthority(auth)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean success = this.contractService.updateDetailsFor(contractId, contractDetailUpdate);

        if (!success) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createContract(
            Authentication auth,
            HttpServletRequest req,
            @RequestBody UpdateContractDetail contractDetail
    ) {
        if (!Authorities.hasManagerOrEmployeeAuthority(auth)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Integer id = this.contractService.createContract(contractDetail);

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        String createdURL = req.getServletPath() + "/" + id;

        return ResponseEntity.created(URI.create(createdURL)).build();
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<?> completeContract(Authentication auth, @PathVariable("id") Integer contractId) {
        if (!Authorities.hasManagerOrEmployeeAuthority(auth)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean completed = this.contractService.completeContract(contractId);

        if (!completed) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign")
    public ResponseEntity<?> signContract(
            Authentication auth,
            @RequestBody SignContractRequest signContractRequest
    ) {
        if (!Authorities.hasCustomerAuthority(auth)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Integer customerId = Authorities.extractSubjectId(auth);

        boolean signed = this.contractService.signContract(customerId, signContractRequest);

        if (!signed) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteContract(
            Authentication auth,
            @RequestBody DeleteRequest<Integer> deleteRequest
    ) {
        if (!Authorities.hasManagerOrEmployeeAuthority(auth)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean deleted = this.contractService.deleteContract(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
