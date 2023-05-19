package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.*;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.incoming.SignContractRequest;
import hr.fer.ika.ikasus.DTO.incoming.UpdateContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;
import hr.fer.ika.ikasus.repository.IzdavanjeRepository;
import hr.fer.ika.ikasus.repository.UgovorRepository;
import hr.fer.ika.ikasus.repository.ZahtjevZaNajmomRepository;
import hr.fer.ika.ikasus.resource.AppImage;
import hr.fer.ika.ikasus.service.ContractService;
import hr.fer.ika.ikasus.service.RentalService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class ContractServiceImpl implements ContractService {
    private final UgovorRepository ugovorRepository;
    private final IzdavanjeRepository izdavanjeRepository;
    private final ZahtjevZaNajmomRepository zahtjevZaNajmomRepository;

    private final RentalService rentalService;

    public ContractServiceImpl(
            UgovorRepository ugovorRepository,
            IzdavanjeRepository izdavanjeRepository,
            ZahtjevZaNajmomRepository zahtjevZaNajmomRepository,
            RentalService rentalService
    ) {
        this.ugovorRepository = ugovorRepository;
        this.izdavanjeRepository = izdavanjeRepository;
        this.zahtjevZaNajmomRepository = zahtjevZaNajmomRepository;
        this.rentalService = rentalService;
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
    public List<ContractMaster> getContractMastersForCustomer(Integer customerId) {
        return this.ugovorRepository.findAll().stream()
                .filter((con) -> con.getIzdavanjes().stream().anyMatch((i) ->
                                i.getIdunajmitelj() != null &&
                                        Objects.equals(i.getIdunajmitelj().getId(), customerId)
                        )
                )
                .map(con -> {
                    ContractMaster master = new ContractMaster();
                    master.setId(con.getId());
                    master.setTitle(con.getNaslov());

                    return master;
                })
                .collect(Collectors.toList());
    }

    private static ContractDetail getDetailsFor(Ugovor contract) {
        ContractDetail detail = new ContractDetail();
        detail.setContent(contract.getSadrzaj());
        detail.setContractTag(contract.getOznugovor());
        detail.setTitle(contract.getNaslov());
        detail.setSigned(contract.getVrijemepotpisa() != null);

        if (contract.getPutdopotpisa() != null) {
            detail.setSignaturePath(contract.getPutdopotpisa().replace(AppImage.STATIC_CONTENT_PREFIX, ""));
        }
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
    public ContractDetail getDetailsFor(Integer contractId) {
        if (contractId == null) {
            return null;
        }

        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(contractId);

        if (contractOpt.isEmpty()) {
            return null;
        }

        return getDetailsFor(contractOpt.get());
    }

    @Override
    public ContractDetail getDetailsFor(Integer customerId, Integer contractId) {
        if (contractId == null) {
            return null;
        }

        Optional<Ugovor> contractOpt = this.ugovorRepository.findById(contractId)
                .filter(c -> c.getIzdavanjes().stream().anyMatch(i ->
                                i.getIdunajmitelj() != null && Objects.equals(i.getIdunajmitelj().getId(), customerId)
                        )
                );

        if (contractOpt.isEmpty()) {
            return null;
        }

        return getDetailsFor(contractOpt.get());
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

    private void activateContractRentalRequests(Ugovor contract) {
        ZahtjevZaNajmom rentalRequest = contract.getIdzahtjev();

        if (rentalRequest == null) {
            return;
        }

        for (Potrazuje rentalReqPart : rentalRequest.getPotrazujes()) {
            CreateRentalDetail rentalDetail = new CreateRentalDetail();
            rentalDetail.setVehicleId(rentalReqPart.getIdvozilo().getId());
            rentalDetail.setContractId(contract.getId());
            rentalDetail.setTimeFrom(
                    Date.from(rentalReqPart.getPotraznjaod().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            rentalDetail.setTimeTo(
                    Date.from(rentalReqPart.getPotraznjado().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            rentalDetail.setKmDriven(0);
            rentalDetail.setActive(true);

            this.rentalService.createRental(rentalDetail);
        }
    }

    @Override
    public boolean signContract(Integer customerId, SignContractRequest signContractRequest) {
        if (customerId == null || signContractRequest == null ||
                signContractRequest.getContractId() == null || signContractRequest.getSignatureBase64Encoded() == null
        ) {
            return false;
        }

        Optional<Ugovor> contrOpt = this.ugovorRepository.findById(signContractRequest.getContractId());

        if (contrOpt.isEmpty()) {
            return false;
        }

        Ugovor contract = contrOpt.get();

        if (contract.getVrijemepotpisa() != null) {
            return false;
        }

        List<Izdavanje> issueOpt = this.izdavanjeRepository.findByIdugovor(contract).stream()
                .filter(is -> is.getIdunajmitelj() != null && Objects.equals(is.getIdunajmitelj().getId(), customerId))
                .collect(Collectors.toList());

        if (issueOpt.isEmpty()) {
            return false;
        }

        AppImage.AppImageBuilder builder = AppImage.fromBase64Builder();

        String imagePath = AppImage.SIGNATURE_IMAGE_ROOT + "signature_" + contract.getId() + "_" + customerId + ".png";
        AppImage signatureImage = builder.withImageData(signContractRequest.getSignatureBase64Encoded())
                .withImagePath(imagePath)
                .build();
        try {
            signatureImage.save();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        contract.setPutdopotpisa(imagePath);
        contract.setVrijemepotpisa(Instant.now());

        if (signContractRequest.getTimeSigned() != null) {
            contract.setVrijemepotpisa(signContractRequest.getTimeSigned().toInstant());
        }

        this.ugovorRepository.save(contract);

        this.activateContractRentalRequests(contract);

        return true;
    }

    @Override
    public boolean canViewSignature(Integer customerId, String pathToSignature) {
        if (customerId == null || pathToSignature == null) {
            return false;
        }

        return this.ugovorRepository.canViewSignature(customerId, pathToSignature);
    }
}
