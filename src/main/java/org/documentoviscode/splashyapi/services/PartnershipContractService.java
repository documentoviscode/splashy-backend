package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.data.requests.PartnershipContractDTO;
import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.repositories.PartnershipContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Update an existing partnership contract.
     *
     * @param id                   The ID of the partnership contract to be updated.
     * @param updatedContract The updated partnership contract data.
     * @return The updated partnership contract or null if the contract with the specified ID is not found.
     */
    public PartnershipContract updatePartnershipContract(Long id, PartnershipContractDTO updatedContract) {
        return findPartnershipContractById(id)
                .map(contractToUpdate -> {
                    contractToUpdate.setType(updatedContract.type());
                    contractToUpdate.setGDriveLink(updatedContract.GDriveLink());
                    contractToUpdate.setCreationDate(updatedContract.creationDate());
                    contractToUpdate.setStartDate(updatedContract.startDate());
                    contractToUpdate.setEndDate(updatedContract.endDate());
                    contractToUpdate.setRate(updatedContract.rate());
                    contractToUpdate.setDonationPercentage(updatedContract.donationPercentage());
                    return partnershipContractRepository.save(contractToUpdate);
                })
                .orElse(null);
    }
}
