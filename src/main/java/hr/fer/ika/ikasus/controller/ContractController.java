package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.incoming.UpdateContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.ContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;
import hr.fer.ika.ikasus.service.ContractService;
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
    public ResponseEntity<List<ContractMaster>> getContracts() {
        return ResponseEntity.ok(this.contractService.getContractMasters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDetail> getContractDetail(@PathVariable("id") Integer contractId) {
        ContractDetail detail = this.contractService.getDetailsFor(contractId);

        if (detail == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(detail);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateContractDetails(
            @PathVariable("id") Integer contractId,
            @RequestBody UpdateContractDetail contractDetailUpdate
    ) {
        boolean success = this.contractService.updateDetailsFor(contractId, contractDetailUpdate);

        if (!success) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createContract(
            HttpServletRequest req,
            @RequestBody UpdateContractDetail contractDetail
    ) {
        Integer id = this.contractService.createContract(contractDetail);

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        String createdURL = req.getServletPath() + "/" + id;

        return ResponseEntity.created(URI.create(createdURL)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteContract(@RequestBody DeleteRequest<Integer> deleteRequest) {
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
