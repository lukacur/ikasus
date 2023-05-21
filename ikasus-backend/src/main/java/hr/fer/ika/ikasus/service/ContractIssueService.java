package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DAO.IzdavanjeId;
import hr.fer.ika.ikasus.DTO.incoming.ContractIssueRequest;

/**
 * @author Luka Ćurić
 */
public interface ContractIssueService {
    IzdavanjeId issueContract(Integer employeeId, ContractIssueRequest contractIssueRequest);
}
