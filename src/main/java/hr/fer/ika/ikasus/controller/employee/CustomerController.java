package hr.fer.ika.ikasus.controller.employee;

import hr.fer.ika.ikasus.DTO.outgoing.CustomerInfo;
import hr.fer.ika.ikasus.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/api/authenticated/employee/customers",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerInfo>> getAllCustomers() {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerInfo> getLocation(@PathVariable("id") Integer customerId) {
        CustomerInfo customer = this.customerService.getCustomer(customerId);

        if (customer == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customer);
    }
}
