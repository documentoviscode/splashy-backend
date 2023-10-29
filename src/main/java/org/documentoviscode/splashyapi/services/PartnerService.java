package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.Partner;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.repositories.PartnerRepository;
import org.documentoviscode.splashyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing partner-related operations.
 */
@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;

    /**
     * Constructor for the PartnerService class.
     *
     * @param partnerRepository The repository for managing partner entities.
     */
    @Autowired
    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    /**
     * Retrieve a partner by a specified ID.
     *
     * @param id The ID of the partner.
     * @return Optional containing the partner or an empty optional if the partner is not found.
     */
    public Optional<Partner> findPartnerById(Long id) {
        return partnerRepository.findById(id);
    }

    /**
     * Retrieve a list of all partners.
     *
     * @return List of all partner entities.
     */
    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }
}
