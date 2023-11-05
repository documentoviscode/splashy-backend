package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.repositories.PartnershipContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Service class for managing partnership contract-related operations.
 */
@Service
public class PartnershipContractService {
    private final PartnershipContractRepository partnershipContractRepository;

    /**
     * Constructor for the PartnershipContractService class.
     *
     * @param partnershipContractRepository The repository for managing partnership contract entities.
     */
    @Autowired
    public PartnershipContractService(PartnershipContractRepository partnershipContractRepository) {
        this.partnershipContractRepository = partnershipContractRepository;
    }

    /**
     * Retrieve a partnership contract by a specified ID.
     *
     * @param id The ID of the partnership contract.
     * @return Optional containing the partnership contract or an empty optional if the contract is not found.
     */
    public Optional<PartnershipContract> findPartnershipContractById(Long id) {
        return partnershipContractRepository.findById(id);
    }

    /**
     * Retrieve a list of all partnership contracts.
     *
     * @return List of all partnership contract entities.
     */
    public List<PartnershipContract> findAll() {
        return partnershipContractRepository.findAll();
    }

    /**
     * Create a new partnership contract by saving it to the repository.
     *
     * @param partnershipContract The partnership contract to be created.
     * @return The created partnership contract.
     */
    @Transactional
    public PartnershipContract create(PartnershipContract partnershipContract) {

        return partnershipContractRepository.save(partnershipContract);
    }
}
