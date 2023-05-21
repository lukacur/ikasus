package hr.fer.ika.ikasus.controller.manager;

import hr.fer.ika.ikasus.DTO.incoming.*;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.EmployeeInfo;
import hr.fer.ika.ikasus.DTO.outgoing.ManagerInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RenterInfo;
import hr.fer.ika.ikasus.service.EmployeeService;
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
        value = "/api/authenticated/manager/employees",
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class EmployeeManagementController {
    private final EmployeeService employeeService;

    public EmployeeManagementController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeInfo>> getEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeInfo> getEmployee(@PathVariable("id") Integer employeeId) {
        EmployeeInfo employeeInfo = this.employeeService.getEmployee(employeeId);

        if (employeeInfo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(employeeInfo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateEmployee(
            @PathVariable("id") Integer employeeId,
            @RequestBody CreateEmployeeRequest createEmployeeRequest
    ) {
        boolean updated = this.employeeService.updateEmployee(employeeId, createEmployeeRequest);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update employee request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createEmployee(
            HttpServletRequest req,
            @RequestBody CreateEmployeeRequest createEmployeeRequest
    ) {
        Integer createdEmployeeId = this.employeeService.createEmployee(createEmployeeRequest);

        if (createdEmployeeId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create employee request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdEmployeeId)).build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteEmployee(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.employeeService.deleteEmployee(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete employee request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/managers")
    public ResponseEntity<List<ManagerInfo>> getManagers() {
        return ResponseEntity.ok(this.employeeService.getAllManagers());
    }

    @GetMapping("/managers/{id}")
    public ResponseEntity<ManagerInfo> getManager(@PathVariable("id") Integer managerId) {
        ManagerInfo managerInfo = this.employeeService.getManager(managerId);

        if (managerInfo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(managerInfo);
    }

    @PatchMapping("/managers/{id}")
    public ResponseEntity<CommonResponse> updateManager(
            @PathVariable("id") Integer managerId,
            @RequestBody UpdateManagerRequest updateManagerRequest
    ) {
        boolean updated = this.employeeService.updateManager(managerId, updateManagerRequest);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update manager request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/managers")
    public ResponseEntity<CommonResponse> createManager(
            HttpServletRequest req,
            @RequestBody CreateManagerRequest createManagerRequest
    ) {
        Integer createdManagerId = this.employeeService.createManager(createManagerRequest);

        if (createdManagerId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create manager request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdManagerId)).build();
    }

    @DeleteMapping("/managers")
    public ResponseEntity<CommonResponse> deleteManager(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.employeeService.deleteManager(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete manager request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/renters")
    public ResponseEntity<List<RenterInfo>> getRenters() {
        return ResponseEntity.ok(this.employeeService.getAllRenters());
    }

    @GetMapping("/renters/{id}")
    public ResponseEntity<RenterInfo> getRenter(@PathVariable("id") Integer renterId) {
        RenterInfo renterInfo = this.employeeService.getRenter(renterId);

        if (renterInfo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(renterInfo);
    }

    @PatchMapping("/renters/{id}")
    public ResponseEntity<CommonResponse> updateRenter(
            @PathVariable("id") Integer renterId,
            @RequestBody UpdateRenterRequest updateRenterRequest
    ) {
        boolean updated = this.employeeService.updateRenter(renterId, updateRenterRequest);

        if (!updated) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid update renter request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/renters")
    public ResponseEntity<CommonResponse> creatRenter(
            HttpServletRequest req,
            @RequestBody CreateRenterRequest createRenterRequest
    ) {
        Integer createdRenterId = this.employeeService.createRenter(createRenterRequest);

        if (createdRenterId == null) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid create renter request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.created(URI.create(req.getServletPath() + "/" + createdRenterId)).build();
    }

    @DeleteMapping("/renters")
    public ResponseEntity<CommonResponse> deleteRenter(@RequestBody DeleteRequest<Integer> deleteRequest) {
        boolean deleted = this.employeeService.deleteRenter(deleteRequest.getId());

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete renter request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
