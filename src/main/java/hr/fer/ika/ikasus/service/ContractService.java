package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.SignContractRequest;
import hr.fer.ika.ikasus.DTO.incoming.UpdateContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface ContractService {
    List<ContractMaster> getContractMasters();
    List<ContractMaster> getContractMastersForCustomer(Integer customerId);

    ContractDetail getDetailsFor(Integer contractId);
    ContractDetail getDetailsFor(Integer customerId, Integer contractId);

    Integer createContract(UpdateContractDetail contractDetail);
    boolean updateDetailsFor(Integer contractId, UpdateContractDetail detail);
    boolean deleteContract(Integer contractId);

    boolean signContract(Integer customerId, SignContractRequest signContractRequest);

    boolean canViewSignature(Integer customerId, String pathToSignature);
}
