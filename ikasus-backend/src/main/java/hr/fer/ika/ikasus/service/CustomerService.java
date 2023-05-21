package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.CreateCustomerInfo;
import hr.fer.ika.ikasus.DTO.outgoing.CustomerInfo;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface CustomerService {
    List<CustomerInfo> getAllCustomers();
    CustomerInfo getCustomer(Integer customerId);
    boolean updateCustomer(Integer customerId, CreateCustomerInfo createCustomerInfo);
    Integer createCustomer(CreateCustomerInfo createCustomerInfo);
    boolean deleteCustomer(Integer customerId);
}
