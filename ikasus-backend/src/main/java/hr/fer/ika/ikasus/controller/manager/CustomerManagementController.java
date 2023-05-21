package hr.fer.ika.ikasus.controller.manager;

import hr.fer.ika.ikasus.DTO.incoming.CreateCustomerInfo;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.service.CustomerService;
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
        value = "/api/authenticated/manager/customers",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class CustomerManagementController {
    private final CustomerService customerService;

    public CustomerManagementController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateCustomer(
            @PathVariable("id") Integer customerId,
            @RequestBody CreateCustomerInfo createCustomerInfo
    ) {
        boolean updated = this.customerService.updateCustomer(customerId, createCustomerInfo);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update customer request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createCustomer(
            HttpServletRequest req,
            @RequestBody CreateCustomerInfo createCustomerInfo
    ) {
        Integer createdCustomerId = this.customerService.createCustomer(createCustomerInfo);

        if (createdCustomerId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create customer request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdCustomerId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteCustomer(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.customerService.deleteCustomer(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete customer request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
