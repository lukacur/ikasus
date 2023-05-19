package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.*;
import hr.fer.ika.ikasus.DTO.incoming.ContractIssueRequest;
import hr.fer.ika.ikasus.repository.*;
import hr.fer.ika.ikasus.service.ContractIssueService;
import hr.fer.ika.ikasus.service.ContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

/**
 * @author Luka Ćurić
 */
@Service
public class ContractIssueServiceImpl implements ContractIssueService {
    private final UgovorRepository ugovorRepository;
    private final IzdavanjeRepository izdavanjeRepository;
    private final ZahtjevZaNajmomRepository zahtjevZaNajmomRepository;
    private final IznajmljivacRepository iznajmljivacRepository;
    private final UnajmiteljRepository unajmiteljRepository;

    private final ContractService contractService;

    public ContractIssueServiceImpl(
            UgovorRepository ugovorRepository,
            IzdavanjeRepository izdavanjeRepository,
            ZahtjevZaNajmomRepository zahtjevZaNajmomRepository,
            IznajmljivacRepository iznajmljivacRepository,
            UnajmiteljRepository unajmiteljRepository,
            ContractService contractService
    ) {
        this.ugovorRepository = ugovorRepository;
        this.izdavanjeRepository = izdavanjeRepository;
        this.zahtjevZaNajmomRepository = zahtjevZaNajmomRepository;
        this.iznajmljivacRepository = iznajmljivacRepository;
        this.unajmiteljRepository = unajmiteljRepository;
        this.contractService = contractService;
    }

    @Override
    @Transactional(rollbackFor = { IllegalStateException.class })
    public IzdavanjeId issueContract(Integer employeeId, ContractIssueRequest contractIssueRequest) {
        if (employeeId == null || contractIssueRequest.getCustomerId() == null ||
                contractIssueRequest.getContractInfo() == null
        ) {
            return null;
        }

        Optional<Iznajmljivac> rentOpt = this.iznajmljivacRepository.findById(employeeId);
        Optional<Unajmitelj> custOpt = this.unajmiteljRepository.findById(contractIssueRequest.getCustomerId());

        if (rentOpt.isEmpty() || custOpt.isEmpty()) {
            return null;
        }

        Unajmitelj customer = custOpt.get();

        Integer contractId = this.contractService.createContract(contractIssueRequest.getContractInfo());

        if (contractId == null) {
            return null;
        }

        Optional<Ugovor> contOpt = this.ugovorRepository.findById(contractId);

        if (contOpt.isEmpty()) {
            return null;
        }

        Ugovor contract = contOpt.get();

        if (contractIssueRequest.getRentalRequestId() != null) {
            Optional<ZahtjevZaNajmom> rentReqOpt = this.zahtjevZaNajmomRepository.findById(
                    contractIssueRequest.getRentalRequestId()
            );

            if (rentReqOpt.isEmpty()) {
                throw new IllegalStateException("Rollback: rental requsest ID couldn't be set");
            }

            contract.setIdzahtjev(rentReqOpt.get());
            this.ugovorRepository.save(contract);
        }

        Izdavanje contractIssue = new Izdavanje();
        IzdavanjeId contractIssueId = new IzdavanjeId();
        contractIssueId.setIdugovor(contract.getId());
        contractIssueId.setIdunajmitelj(customer.getId());

        contractIssue.setId(contractIssueId);

        LocalDate issueDate = LocalDate.now();

        if (contractIssueRequest.getDateOfIssue() != null) {
            issueDate = contractIssueRequest.getDateOfIssue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        contractIssue.setDatumizdavanja(issueDate);
        contractIssue.setIdiznajmljivac(rentOpt.get());
        contractIssue.setIdunajmitelj(custOpt.get());
        contractIssue.setIdugovor(contract);

        contractIssue = this.izdavanjeRepository.save(contractIssue);

        return contractIssue.getId();
    }
}
