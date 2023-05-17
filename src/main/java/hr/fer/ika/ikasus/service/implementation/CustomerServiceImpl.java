package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Unajmitelj;
import hr.fer.ika.ikasus.DTO.incoming.CreateCustomerInfo;
import hr.fer.ika.ikasus.DTO.outgoing.CustomerInfo;
import hr.fer.ika.ikasus.repository.UnajmiteljRepository;
import hr.fer.ika.ikasus.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final UnajmiteljRepository unajmiteljRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(UnajmiteljRepository unajmiteljRepository, PasswordEncoder passwordEncoder) {
        this.unajmiteljRepository = unajmiteljRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<CustomerInfo> getAllCustomers() {
        return this.unajmiteljRepository.findAll().stream()
                .map(c -> {
                    CustomerInfo locationInfo = new CustomerInfo();
                    locationInfo.setId(c.getId());
                    locationInfo.setName(c.getIme());
                    locationInfo.setSurname(c.getPrezime());
                    locationInfo.setEmail(c.getEmail());
                    locationInfo.setPhoneNumber(c.getBrojtelefona());

                    return locationInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerInfo getCustomer(Integer customerId) {
        if (customerId == null) {
            return null;
        }

        Optional<Unajmitelj> custOpt = this.unajmiteljRepository.findById(customerId);

        if (custOpt.isEmpty()) {
            return null;
        }

        return custOpt.map(c -> {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setId(c.getId());
            customerInfo.setName(c.getIme());
            customerInfo.setSurname(c.getPrezime());
            customerInfo.setEmail(c.getEmail());
            customerInfo.setPhoneNumber(c.getBrojtelefona());

            return customerInfo;
        }).get();
    }

    @Override
    public boolean updateCustomer(Integer customerId, CreateCustomerInfo createCustomerInfo) {
        if (customerId == null) {
            return false;
        }

        Optional<Unajmitelj> custOpt = this.unajmiteljRepository.findById(customerId);

        if (custOpt.isEmpty()) {
            return false;
        }

        Unajmitelj customer = custOpt.get();
        if (createCustomerInfo.getName() != null) {
            customer.setIme(createCustomerInfo.getName());
        }

        if (createCustomerInfo.getSurname() != null) {
            customer.setPrezime(createCustomerInfo.getSurname());
        }

        if (createCustomerInfo.getOib() != null) {
            customer.setOib(createCustomerInfo.getOib());
        }

        if (createCustomerInfo.getEmail() != null) {
            customer.setEmail(createCustomerInfo.getEmail());
        }

        if (createCustomerInfo.getPassword() != null) {
            customer.setLozinka(this.passwordEncoder.encode(createCustomerInfo.getPassword()));
        }

        if (createCustomerInfo.getPhoneNumber() != null) {
            customer.setBrojtelefona(createCustomerInfo.getPhoneNumber());
        }

        this.unajmiteljRepository.save(customer);

        return true;
    }

    @Override
    public Integer createCustomer(CreateCustomerInfo createCustomerInfo) {
        Unajmitelj customer = new Unajmitelj();
        customer.setIme(createCustomerInfo.getName());
        customer.setPrezime(createCustomerInfo.getSurname());
        customer.setOib(createCustomerInfo.getOib());
        customer.setEmail(createCustomerInfo.getEmail());
        customer.setLozinka(this.passwordEncoder.encode(createCustomerInfo.getPassword()));
        customer.setBrojtelefona(createCustomerInfo.getPhoneNumber());

        customer = this.unajmiteljRepository.save(customer);

        return customer.getId();
    }

    @Override
    public boolean deleteCustomer(Integer customerId) {
        if (customerId == null) {
            return false;
        }

        Optional<Unajmitelj> custOpt = this.unajmiteljRepository.findById(customerId);

        if (custOpt.isEmpty()) {
            return false;
        }

        this.unajmiteljRepository.delete(custOpt.get());

        return true;
    }
}
