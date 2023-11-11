package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.data.requests.AdditionalPackageDTO;
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

    /**
     * Update an existing additional package.
     *
     * @param id                     The ID of the additional package to be updated.
     * @param updatedAdditionalPackage The updated additional package data.
     * @return The updated additional package or null if the package with the specified ID is not found.
     */
    public AdditionalPackage updateAdditionalPackage(Long id, AdditionalPackageDTO updatedAdditionalPackage) {
        return findAdditionalPackageById(id)
                .map(packageToUpdate -> {
                    if (updatedAdditionalPackage.getPackageType() != null) {
                        packageToUpdate.setPackageType(updatedAdditionalPackage.getPackageType());
                    }
                    if (updatedAdditionalPackage.getGDriveLink() != null) {
                        packageToUpdate.setGDriveLink(updatedAdditionalPackage.getGDriveLink());
                    }
                    if (updatedAdditionalPackage.getCreationDate() != null) {
                        packageToUpdate.setCreationDate(updatedAdditionalPackage.getCreationDate());
                    }
                    if (updatedAdditionalPackage.getPackageType() != null) {
                        packageToUpdate.setPackageType(updatedAdditionalPackage.getPackageType());
                    }
                    if (updatedAdditionalPackage.getPrice() != null) {
                        packageToUpdate.setPrice(updatedAdditionalPackage.getPrice());
                    }
                    return additionalPackageRepository.save(packageToUpdate);
                })
                .orElse(null);
    }
}
