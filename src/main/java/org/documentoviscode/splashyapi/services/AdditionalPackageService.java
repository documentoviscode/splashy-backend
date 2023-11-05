package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.repositories.AdditionalPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing additional package-related operations.
 */
@Service
public class AdditionalPackageService {
    private final AdditionalPackageRepository additionalPackageRepository;

    /**
     * Constructor for the AdditionalPackageService class.
     *
     * @param additionalPackageRepository The repository for managing additional package entities.
     */
    @Autowired
    public AdditionalPackageService(AdditionalPackageRepository additionalPackageRepository) {
        this.additionalPackageRepository = additionalPackageRepository;
    }

    /**
     * Retrieve an additional package by a specified ID.
     *
     * @param id The ID of the additional package.
     * @return Optional containing the additional package or an empty optional if the package is not found.
     */
    public Optional<AdditionalPackage> findAdditionalPackageById(Long id) {
        return additionalPackageRepository.findById(id);
    }

    /**
     * Retrieve a list of all additional packages.
     *
     * @return List of all additional package entities.
     */
    public List<AdditionalPackage> findAll() {
        return additionalPackageRepository.findAll();
    }

    /**
     * Create a new additional package by saving it to the repository.
     *
     * @param additionalPackage The additional package to be created.
     * @return The created additional package.
     */
    @Transactional
    public AdditionalPackage create(AdditionalPackage additionalPackage)
    {
        return additionalPackageRepository.save(additionalPackage);
    }
}
