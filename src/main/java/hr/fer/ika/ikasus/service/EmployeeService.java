package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.*;
import hr.fer.ika.ikasus.DTO.outgoing.EmployeeInfo;
import hr.fer.ika.ikasus.DTO.outgoing.ManagerInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RenterInfo;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface EmployeeService {
    List<EmployeeInfo> getAllEmployees();
    EmployeeInfo getEmployee(Integer employeeId);
    boolean updateEmployee(Integer employeeId, CreateEmployeeRequest createEmployeeRequest);
    Integer createEmployee(CreateEmployeeRequest createEmployeeRequest);
    boolean deleteEmployee(Integer employeeId);

    List<ManagerInfo> getAllManagers();
    ManagerInfo getManager(Integer managerId);
    boolean updateManager(Integer managerId, UpdateManagerRequest updateManagerRequest);
    Integer createManager(CreateManagerRequest createManagerRequest);
    boolean deleteManager(Integer managerId);

    List<RenterInfo> getAllRenters();
    RenterInfo getRenter(Integer renterId);
    boolean updateRenter(Integer renterId, UpdateRenterRequest updateRenterRequest);
    Integer createRenter(CreateRenterRequest createRenterRequest);
    boolean deleteRenter(Integer renterId);
}
