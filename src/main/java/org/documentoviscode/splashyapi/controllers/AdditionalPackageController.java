package org.documentoviscode.splashyapi.controllers;



import org.documentoviscode.splashyapi.data.requests.AdditionalPackageDTO;
import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.services.AdditionalPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing additional packages-related operations.
 */
@RestController
@RequestMapping("/additionalPackages")
public class AdditionalPackageController {
    private final AdditionalPackageService additionalPackageService;

    /**
     * Constructor for the AdditionalPackageController class.
     *
     * @param additionalPackageService The service for managing additional package-related operations.
     */
    @Autowired
    public AdditionalPackageController(AdditionalPackageService additionalPackageService) {
        this.additionalPackageService = additionalPackageService;
    }

    /**
     * Retrieve a list of all additional packages.
     *
     * @return ResponseEntity containing the list of all additional package entities.
     */
    @GetMapping("")
    public ResponseEntity<List<AdditionalPackage>> getAllAdditionalPackages() {
        return ResponseEntity.ok(additionalPackageService.findAll());
    }

    /**
     * Retrieve an additional package by a specified ID.
     *
     * @param id The ID of the additional package to retrieve.
     * @return ResponseEntity containing the additional package or a "not found" response if the package is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalPackage> getAdditionalPackage(@PathVariable("id") long id) {
        Optional<AdditionalPackage> additionalPackage = additionalPackageService.findAdditionalPackageById(id);
        return additionalPackage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update an existing additional package based on its ID.
     *
     * @param id                       The ID of the additional package to be updated.
     * @param updatedAdditionalPackage  The updated additional package data.
     * @return A ResponseEntity containing the updated additional package if successful, or a not found status if the package with the specified ID is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AdditionalPackage> updateAdditionalPackage(@PathVariable Long id, @RequestBody AdditionalPackageDTO updatedAdditionalPackage) {
        AdditionalPackage updated = additionalPackageService.updateAdditionalPackage(id, updatedAdditionalPackage);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
