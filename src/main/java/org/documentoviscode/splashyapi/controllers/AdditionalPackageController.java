package org.documentoviscode.splashyapi.controllers;



import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.documentoviscode.splashyapi.data.requests.AdditionalPackageDTO;
import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.dto.CreateAdditionalPackageDto;
import org.documentoviscode.splashyapi.services.AdditionalPackageService;
import org.documentoviscode.splashyapi.services.UserService;
import org.documentoviscode.splashyapi.utility.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing additional packages-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/additionalPackages")
public class AdditionalPackageController {
    private final AdditionalPackageService additionalPackageService;
    private final UserService userService;
    private final EmailService emailService;

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
     * Create a new AdditionalPackage for a specified user.
     *
     * @param additionalPackageDto The additional package DTO to create additional package entity.
     * @param userId          The ID of the user for whom the additional package is created.
     * @return ResponseEntity containing the created additional package and HTTP status.
     */
    @PostMapping
    public ResponseEntity<AdditionalPackage> createAdditionalPackage(@RequestBody CreateAdditionalPackageDto additionalPackageDto, @RequestParam Long userId ) throws MessagingException {
        Optional<User> userOptional = userService.findUserById(userId);

        if(userOptional.isPresent())
        {
            AdditionalPackage newAdditionalPackage = CreateAdditionalPackageDto
                    .dtoToEntityMapper()
                    .apply(additionalPackageDto);

            newAdditionalPackage.setUser(userOptional.get());
            AdditionalPackage createdAdditionalPackage = additionalPackageService.create(newAdditionalPackage);

            if(createdAdditionalPackage!=null)
            {
                Double price = Math.ceil((createdAdditionalPackage.getPrice() * 123d)) / 100.0;
                String stringPrice = String.format("%.2f", price);
                emailService.sendShortenedFacture("michalziemiec@wp.pl", userOptional.get().getName(), createdAdditionalPackage.getPackageType(), stringPrice);
                return new ResponseEntity<>(createdAdditionalPackage, HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
