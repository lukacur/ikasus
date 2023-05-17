package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.UpdateContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractDetail;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface ContractService {
    List<ContractMaster> getContractMasters();
    ContractDetail getDetailsFor(Integer contractId);
    Integer createContract(UpdateContractDetail contractDetail);
    boolean updateDetailsFor(Integer contractId, UpdateContractDetail detail);
    boolean deleteContract(Integer contractId);
}
