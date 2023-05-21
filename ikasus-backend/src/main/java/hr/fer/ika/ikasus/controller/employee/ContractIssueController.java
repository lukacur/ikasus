package hr.fer.ika.ikasus.controller.employee;

import hr.fer.ika.ikasus.DAO.IzdavanjeId;
import hr.fer.ika.ikasus.DTO.incoming.ContractIssueRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.controller.ContractController;
import hr.fer.ika.ikasus.service.ContractIssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/employee/issue-contract",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class ContractIssueController {
    private final ContractIssueService contractIssueService;

    public ContractIssueController(ContractIssueService contractIssueService) {
        this.contractIssueService = contractIssueService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> issueContract(
            Authentication auth,
            @RequestBody ContractIssueRequest contractIssueRequest
    ) {
        Integer employeeId = Authorities.extractSubjectId(auth);

        if (employeeId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        IzdavanjeId issuedContractId = this.contractIssueService.issueContract(employeeId, contractIssueRequest);

        if (issuedContractId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid issue contract request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(
                URI.create(ContractController.ROOT_PATH + "/" + issuedContractId.getIdugovor())
        ).build();
    }
}
