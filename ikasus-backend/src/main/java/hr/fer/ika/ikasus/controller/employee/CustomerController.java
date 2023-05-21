package hr.fer.ika.ikasus.controller.employee;

import hr.fer.ika.ikasus.DTO.outgoing.CustomerInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RatingInfo;
import hr.fer.ika.ikasus.service.CustomerService;
import hr.fer.ika.ikasus.service.RatingService;
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
    private final RatingService ratingService;

    public CustomerController(CustomerService customerService, RatingService ratingService) {
        this.customerService = customerService;
        this.ratingService = ratingService;
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

    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<RatingInfo>> getRatingsByUser(@PathVariable("id") Integer customerId) {
        List<RatingInfo> customer = this.ratingService.getRatingsByCustomer(customerId);

        if (customer == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customer);
    }
}
