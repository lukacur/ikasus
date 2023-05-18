package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Iznajmljivac;
import hr.fer.ika.ikasus.DAO.Lokacija;
import hr.fer.ika.ikasus.DAO.UpraviteljPoslovnice;
import hr.fer.ika.ikasus.DAO.Zaposlenik;
import hr.fer.ika.ikasus.DTO.incoming.*;
import hr.fer.ika.ikasus.DTO.outgoing.EmployeeInfo;
import hr.fer.ika.ikasus.DTO.outgoing.ManagerInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RenterInfo;
import hr.fer.ika.ikasus.repository.IznajmljivacRepository;
import hr.fer.ika.ikasus.repository.LokacijaRepository;
import hr.fer.ika.ikasus.repository.UpraviteljPoslovniceRepository;
import hr.fer.ika.ikasus.repository.ZaposlenikRepository;
import hr.fer.ika.ikasus.service.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final ZaposlenikRepository zaposlenikRepository;
    private final UpraviteljPoslovniceRepository upraviteljPoslovniceRepository;
    private final IznajmljivacRepository iznajmljivacRepository;
    private final LokacijaRepository lokacijaRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(
            ZaposlenikRepository zaposlenikRepository,
            UpraviteljPoslovniceRepository upraviteljPoslovniceRepository,
            IznajmljivacRepository iznajmljivacRepository,
            LokacijaRepository lokacijaRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.zaposlenikRepository = zaposlenikRepository;
        this.upraviteljPoslovniceRepository = upraviteljPoslovniceRepository;
        this.iznajmljivacRepository = iznajmljivacRepository;
        this.lokacijaRepository = lokacijaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<EmployeeInfo> getAllEmployees() {
        return this.zaposlenikRepository.findAll().stream()
                .map(e -> {
                    Lokacija loc = e.getIdlokacija();

                    EmployeeInfo employeeInfo = new EmployeeInfo();
                    employeeInfo.setId(e.getId());
                    employeeInfo.setName(e.getIme());
                    employeeInfo.setSurname(e.getPrezime());
                    employeeInfo.setOib(e.getOib());
                    if (loc != null) {
                        employeeInfo.setIdLocation(loc.getId());
                        employeeInfo.setWorkAddress(loc.getAdresa());
                    }

                    return employeeInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeInfo getEmployee(Integer employeeId) {
        if (employeeId == null) {
            return null;
        }

        Optional<Zaposlenik> empOpt = this.zaposlenikRepository.findById(employeeId);

        if (empOpt.isEmpty()) {
            return null;
        }

        return empOpt.map(e -> {
            Lokacija loc = e.getIdlokacija();

            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setId(e.getId());
            employeeInfo.setName(e.getIme());
            employeeInfo.setSurname(e.getPrezime());
            employeeInfo.setOib(e.getOib());
            if (loc != null) {
                employeeInfo.setIdLocation(loc.getId());
                employeeInfo.setWorkAddress(loc.getAdresa());
            }

            return employeeInfo;
        }).get();
    }

    @Override
    public boolean updateEmployee(Integer employeeId, CreateEmployeeRequest createEmployeeRequest) {
        if (employeeId == null || createEmployeeRequest == null) {
            return false;
        }

        Optional<Zaposlenik> empOpt = this.zaposlenikRepository.findById(employeeId);

        if (empOpt.isEmpty()) {
            return false;
        }

        Zaposlenik employee = empOpt.get();

        if (createEmployeeRequest.getIdLocation() != null) {
            Optional<Lokacija> locOpt = this.lokacijaRepository.findById(createEmployeeRequest.getIdLocation());

            if (locOpt.isEmpty()) {
                return false;
            }

            employee.setIdlokacija(locOpt.get());
        }

        if (createEmployeeRequest.getOib() != null) {
            employee.setOib(createEmployeeRequest.getOib());
        }

        if (createEmployeeRequest.getName() != null) {
            employee.setIme(createEmployeeRequest.getName());
        }

        if (createEmployeeRequest.getSurname() != null) {
            employee.setPrezime(createEmployeeRequest.getSurname());
        }

        this.zaposlenikRepository.save(employee);

        return true;
    }

    @Override
    public Integer createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        Zaposlenik employee = new Zaposlenik();
        if (createEmployeeRequest.getIdLocation() != null) {
            Optional<Lokacija> locOpt = this.lokacijaRepository.findById(createEmployeeRequest.getIdLocation());

            if (locOpt.isEmpty()) {
                return null;
            }

            employee.setIdlokacija(locOpt.get());
        }
        employee.setOib(createEmployeeRequest.getOib());
        employee.setIme(createEmployeeRequest.getName());
        employee.setPrezime(createEmployeeRequest.getSurname());

        employee = this.zaposlenikRepository.save(employee);

        return employee.getId();
    }

    @Override
    public boolean deleteEmployee(Integer employeeId) {
        if (employeeId == null) {
            return false;
        }

        Optional<Zaposlenik> empOpt = this.zaposlenikRepository.findById(employeeId);

        if (empOpt.isEmpty()) {
            return false;
        }

        this.zaposlenikRepository.delete(empOpt.get());

        return true;
    }

    @Override
    public List<ManagerInfo> getAllManagers() {
        return this.upraviteljPoslovniceRepository.findAll().stream()
                .map(sm -> {
                    Zaposlenik emp = sm.getZaposlenik();
                    Lokacija loc = emp.getIdlokacija();

                    EmployeeInfo employeeInfo = new EmployeeInfo();
                    employeeInfo.setId(emp.getId());
                    employeeInfo.setName(emp.getIme());
                    employeeInfo.setSurname(emp.getPrezime());
                    employeeInfo.setOib(emp.getOib());
                    if (loc != null) {
                        employeeInfo.setIdLocation(loc.getId());
                        employeeInfo.setWorkAddress(loc.getAdresa());
                    }

                    ManagerInfo managerInfo = new ManagerInfo();
                    managerInfo.setEmployeeInfo(employeeInfo);
                    managerInfo.setUsername(sm.getKorisnickoime());
                    if (sm.getUpraviteljod() != null) {
                        managerInfo.setManagerFrom(
                                Date.from(sm.getUpraviteljod().atStartOfDay(ZoneId.systemDefault()).toInstant())
                        );
                    }

                    if (sm.getUpraviteljdo() != null) {
                        managerInfo.setManagerTo(
                                Date.from(sm.getUpraviteljdo().atStartOfDay(ZoneId.systemDefault()).toInstant())
                        );
                    }

                    return managerInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ManagerInfo getManager(Integer managerId) {
        if (managerId == null) {
            return null;
        }

        Optional<UpraviteljPoslovnice> mgrOpt = this.upraviteljPoslovniceRepository.findById(managerId);

        if (mgrOpt.isEmpty()) {
            return null;
        }

        return mgrOpt.map(sm -> {
            Zaposlenik emp = sm.getZaposlenik();
            Lokacija loc = emp.getIdlokacija();

            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setId(emp.getId());
            employeeInfo.setName(emp.getIme());
            employeeInfo.setSurname(emp.getPrezime());
            employeeInfo.setOib(emp.getOib());
            if (loc != null) {
                employeeInfo.setIdLocation(loc.getId());
                employeeInfo.setWorkAddress(loc.getAdresa());
            }

            ManagerInfo managerInfo = new ManagerInfo();
            managerInfo.setEmployeeInfo(employeeInfo);
            managerInfo.setUsername(sm.getKorisnickoime());
            if (sm.getUpraviteljod() != null) {
                managerInfo.setManagerFrom(
                        Date.from(sm.getUpraviteljod().atStartOfDay(ZoneId.systemDefault()).toInstant())
                );
            }

            if (sm.getUpraviteljdo() != null) {
                managerInfo.setManagerTo(
                        Date.from(sm.getUpraviteljdo().atStartOfDay(ZoneId.systemDefault()).toInstant())
                );
            }

            return managerInfo;
        }).get();
    }

    @Override
    public boolean updateManager(Integer managerId, UpdateManagerRequest updateManagerRequest) {
        if (managerId == null) {
            return false;
        }

        Optional<Zaposlenik> empOpt = this.zaposlenikRepository.findById(managerId);
        Optional<UpraviteljPoslovnice> smOpt = this.upraviteljPoslovniceRepository.findById(managerId);

        if (empOpt.isEmpty() || smOpt.isEmpty()) {
            return false;
        }

        UpraviteljPoslovnice storeManager = smOpt.get();

        if (updateManagerRequest.getUsername() != null) {
            storeManager.setKorisnickoime(updateManagerRequest.getUsername());
        }

        if (updateManagerRequest.getPassword() != null) {
            storeManager.setLozinka(this.passwordEncoder.encode(updateManagerRequest.getPassword()));
        }

        if (updateManagerRequest.getManagerFrom() != null) {
            storeManager.setUpraviteljod(
                    updateManagerRequest.getManagerFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        if (updateManagerRequest.getManagerTo() != null) {
            storeManager.setUpraviteljdo(
                    updateManagerRequest.getManagerFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        Zaposlenik employee = empOpt.get();
        this.updateEmployee(employee.getId(), updateManagerRequest.getCreateEmployeeRequest());

        this.upraviteljPoslovniceRepository.save(storeManager);

        return true;
    }

    @Override
    public Integer createManager(CreateManagerRequest createManagerRequest) {
        if (createManagerRequest.getEmployeeId() != null && createManagerRequest.getCreateEmployeeRequest() != null ||
                createManagerRequest.getEmployeeId() == null && createManagerRequest.getCreateEmployeeRequest() == null
        ) {
            return null;
        }

        Optional<Zaposlenik> empOpt;

        if (createManagerRequest.getEmployeeId() != null) {
            empOpt = this.zaposlenikRepository.findById(createManagerRequest.getEmployeeId());
        } else {
            Integer empId = this.createEmployee(createManagerRequest.getCreateEmployeeRequest());

            if (empId == null) {
                return null;
            }

            empOpt = this.zaposlenikRepository.findById(empId);
        }

        if (empOpt.isEmpty()) {
            return null;
        }

        Zaposlenik employee = empOpt.get();

        UpraviteljPoslovnice storeManager = new UpraviteljPoslovnice();
        storeManager.setZaposlenik(employee);
        storeManager.setKorisnickoime(createManagerRequest.getUsername());
        storeManager.setLozinka(this.passwordEncoder.encode(createManagerRequest.getPassword()));
        storeManager.setUpraviteljod(
                createManagerRequest.getManagerFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        if (createManagerRequest.getManagerTo() != null) {
            storeManager.setUpraviteljdo(
                    createManagerRequest.getManagerTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        storeManager = this.upraviteljPoslovniceRepository.save(storeManager);

        return storeManager.getId();
    }

    @Override
    public boolean deleteManager(Integer managerId) {
        if (managerId == null) {
            return false;
        }

        Optional<UpraviteljPoslovnice> smOpt = this.upraviteljPoslovniceRepository.findById(managerId);

        if (smOpt.isEmpty()) {
            return false;
        }

        this.upraviteljPoslovniceRepository.delete(smOpt.get());

        return true;
    }

    @Override
    public List<RenterInfo> getAllRenters() {
        return this.iznajmljivacRepository.findAll().stream()
                .map(r -> {
                    Zaposlenik emp = r.getZaposlenik();
                    Lokacija loc = emp.getIdlokacija();

                    EmployeeInfo employeeInfo = new EmployeeInfo();
                    employeeInfo.setId(emp.getId());
                    employeeInfo.setName(emp.getIme());
                    employeeInfo.setSurname(emp.getPrezime());
                    employeeInfo.setOib(emp.getOib());
                    if (loc != null) {
                        employeeInfo.setIdLocation(loc.getId());
                        employeeInfo.setWorkAddress(loc.getAdresa());
                    }

                    RenterInfo renterInfo = new RenterInfo();
                    renterInfo.setEmployeeInfo(employeeInfo);
                    renterInfo.setEmail(r.getEmail());
                    renterInfo.setPhoneNumber(r.getBrojtelefona());

                    return renterInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RenterInfo getRenter(Integer renterId) {
        if (renterId == null) {
            return null;
        }

        Optional<Iznajmljivac> rentOpt = this.iznajmljivacRepository.findById(renterId);

        if (rentOpt.isEmpty()) {
            return null;
        }

        return rentOpt.map(r -> {
            Zaposlenik emp = r.getZaposlenik();
            Lokacija loc = emp.getIdlokacija();

            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setId(emp.getId());
            employeeInfo.setName(emp.getIme());
            employeeInfo.setSurname(emp.getPrezime());
            employeeInfo.setOib(emp.getOib());
            if (loc != null) {
                employeeInfo.setIdLocation(loc.getId());
                employeeInfo.setWorkAddress(loc.getAdresa());
            }

            RenterInfo renterInfo = new RenterInfo();
            renterInfo.setEmployeeInfo(employeeInfo);
            renterInfo.setEmail(r.getEmail());
            renterInfo.setPhoneNumber(r.getBrojtelefona());

            return renterInfo;
        }).get();
    }

    @Override
    public boolean updateRenter(Integer renterId, UpdateRenterRequest updateRenterRequest) {
        if (renterId == null) {
            return false;
        }

        Optional<Zaposlenik> empOpt = this.zaposlenikRepository.findById(renterId);
        Optional<Iznajmljivac> rentOpt = this.iznajmljivacRepository.findById(renterId);

        if (empOpt.isEmpty() || rentOpt.isEmpty()) {
            return false;
        }

        Iznajmljivac renter = rentOpt.get();

        if (updateRenterRequest.getEmail() != null) {
            renter.setEmail(updateRenterRequest.getEmail());
        }

        if (updateRenterRequest.getPassword() != null) {
            renter.setLozinka(this.passwordEncoder.encode(updateRenterRequest.getPassword()));
        }

        if (updateRenterRequest.getPhoneNumber() != null) {
            renter.setBrojtelefona(updateRenterRequest.getPhoneNumber());
        }

        Zaposlenik employee = empOpt.get();
        this.updateEmployee(employee.getId(), updateRenterRequest.getCreateEmployeeRequest());

        this.iznajmljivacRepository.save(renter);

        return true;
    }

    @Override
    public Integer createRenter(CreateRenterRequest createRenterRequest) {
        if (createRenterRequest.getEmployeeId() != null && createRenterRequest.getCreateEmployeeRequest() != null ||
                createRenterRequest.getEmployeeId() == null && createRenterRequest.getCreateEmployeeRequest() == null
        ) {
            return null;
        }

        Optional<Zaposlenik> empOpt;

        if (createRenterRequest.getEmployeeId() != null) {
            empOpt = this.zaposlenikRepository.findById(createRenterRequest.getEmployeeId());
        } else {
            Integer empId = this.createEmployee(createRenterRequest.getCreateEmployeeRequest());

            if (empId == null) {
                return null;
            }

            empOpt = this.zaposlenikRepository.findById(empId);
        }

        if (empOpt.isEmpty()) {
            return null;
        }

        Zaposlenik employee = empOpt.get();

        Iznajmljivac renter = new Iznajmljivac();
        renter.setZaposlenik(employee);
        renter.setEmail(createRenterRequest.getEmail());
        renter.setLozinka(this.passwordEncoder.encode(createRenterRequest.getPassword()));
        renter.setBrojtelefona(createRenterRequest.getPhoneNumber());

        renter = this.iznajmljivacRepository.save(renter);

        return renter.getId();
    }

    @Override
    public boolean deleteRenter(Integer renterId) {
        if (renterId == null) {
            return false;
        }

        Optional<Iznajmljivac> rentOpt = this.iznajmljivacRepository.findById(renterId);

        if (rentOpt.isEmpty()) {
            return false;
        }

        this.iznajmljivacRepository.delete(rentOpt.get());

        return true;
    }
}
