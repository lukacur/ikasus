package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Ugovor;
import hr.fer.ika.ikasus.DAO.ZahtjevZaNajmom;
import hr.fer.ika.ikasus.DTO.incoming.UpdateContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;
import hr.fer.ika.ikasus.repository.UgovorRepository;
import hr.fer.ika.ikasus.repository.ZahtjevZaNajmomRepository;
import hr.fer.ika.ikasus.service.ContractService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class ContractServiceImpl implements ContractService {
    private final UgovorRepository ugovorRepository;
    private final ZahtjevZaNajmomRepository zahtjevZaNajmomRepository;

    public ContractServiceImpl(UgovorRepository ugovorRepository, ZahtjevZaNajmomRepository zahtjevZaNajmomRepository) {
        this.ugovorRepository = ugovorRepository;
        this.zahtjevZaNajmomRepository = zahtjevZaNajmomRepository;
    }

    @Override
    public List<ContractMaster> getContractMasters() {
        return this.ugovorRepository.findAll().stream()
                .map(con -> {
                    ContractMaster master = new ContractMaster();
                    master.setId(con.getId());
                    master.setTitle(con.getNaslov());

                    return master;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ContractDetail getDetailsFor(Integer contractId) {
        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(contractId);

        if (contractOpt.isEmpty()) {
            return null;
        }

        Ugovor contract = contractOpt.get();

        ContractDetail detail = new ContractDetail();
        detail.setContent(contract.getSadrzaj());
        detail.setContractTag(contract.getOznugovor());
        detail.setTitle(contract.getNaslov());
        detail.setSigned(contract.getVrijemepotpisa() != null);
        detail.setPrice(contract.getUgovorenacijena().doubleValue());

        if (contract.getIdzahtjev() != null) {
            detail.setRentalRequestId(contract.getIdzahtjev().getId());
        }

        if (contract.getVrijemepotpisa() != null) {
            detail.setSignedOn(Date.from(contract.getVrijemepotpisa()));
        }

        return detail;
    }

    @Override
    public Integer createContract(UpdateContractDetail contractDetail) {
        Ugovor contract = new Ugovor();
        contract.setOznugovor(contractDetail.getContractTag());
        contract.setNaslov(contractDetail.getTitle());
        contract.setSadrzaj(contractDetail.getContent());
        contract.setUgovorenacijena(BigDecimal.valueOf(contractDetail.getPrice()));

        if (contractDetail.getRentalRequestId() != null) {
            Optional<ZahtjevZaNajmom> rentReqOpt = this.zahtjevZaNajmomRepository.findById(
                    contractDetail.getRentalRequestId()
            );

            if (rentReqOpt.isEmpty()) {
                return null;
            }

            contract.setIdzahtjev(rentReqOpt.get());
        }

        contract = this.ugovorRepository.save(contract);

        return contract.getId();
    }

    @Override
    public boolean updateDetailsFor(Integer contractId, UpdateContractDetail detail) {
        if (contractId == null) {
            return false;
        }

        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(contractId);

        if (contractOpt.isEmpty()) {
            return false;
        }

        Ugovor contract = contractOpt.get();

        if (detail.getTitle() != null) {
            contract.setNaslov(detail.getTitle());
        }

        if (detail.getContractTag() != null) {
            contract.setOznugovor(detail.getContractTag());
        }

        if (detail.getContent() != null) {
            contract.setSadrzaj(detail.getContent());
        }

        if (detail.getPrice() != null) {
            contract.setUgovorenacijena(BigDecimal.valueOf(detail.getPrice()));
        }

        if (detail.getSignedOn() != null || detail.getSigned() == null || !detail.getSigned()) {
            contract.setVrijemepotpisa(
                    (detail.getSigned() == null || !detail.getSigned()) ? null : detail.getSignedOn().toInstant()
            );
        }

        if (detail.getRentalRequestId() != null) {
            Optional<ZahtjevZaNajmom> rentReqOpt = this.zahtjevZaNajmomRepository.findById(
                    detail.getRentalRequestId()
            );

            if (rentReqOpt.isEmpty()) {
                return false;
            }

            contract.setIdzahtjev(rentReqOpt.get());
        }

        this.ugovorRepository.save(contract);

        return true;
    }

    @Override
    public boolean deleteContract(Integer contractId) {
        if (contractId == null) {
            return false;
        }

        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(contractId);

        if (contractOpt.isEmpty()) {
            return false;
        }

        this.ugovorRepository.delete(contractOpt.get());

        return true;
    }
}
