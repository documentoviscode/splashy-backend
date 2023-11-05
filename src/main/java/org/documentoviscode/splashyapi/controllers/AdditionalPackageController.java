package org.documentoviscode.splashyapi.controllers;



import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.services.AdditionalPackageService;
import org.documentoviscode.splashyapi.services.UserService;
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
@RequestMapping("/additionalPackages")
public class AdditionalPackageController {
    private final AdditionalPackageService additionalPackageService;
    private final UserService userService;

    /**
     * Constructor for the AdditionalPackageController class.
     *
     * @param additionalPackageService The service for managing additional package-related operations.
     */
    @Autowired
    public AdditionalPackageController(AdditionalPackageService additionalPackageService, UserService userService) {
        this.additionalPackageService = additionalPackageService;
        this.userService = userService;
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


    @PostMapping
    public ResponseEntity<AdditionalPackage> createAdditionalPackage(@RequestBody AdditionalPackage newAdditionalPackage, @RequestParam Long userId )
    {
        Optional<User> userOptional = userService.findUserById(userId);

        if(userOptional.isPresent())
        {
            newAdditionalPackage.setUser(userOptional.get());
            AdditionalPackage createdAdditionalPackage = additionalPackageService.create(newAdditionalPackage);
            if(createdAdditionalPackage!=null)
            {
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
}
