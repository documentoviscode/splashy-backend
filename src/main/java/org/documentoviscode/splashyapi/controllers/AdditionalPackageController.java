package org.documentoviscode.splashyapi.controllers;



import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.services.AdditionalPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/additionalPackages")
public class AdditionalPackageController {

    private final AdditionalPackageService additionalPackageService;


    @Autowired
    public AdditionalPackageController(AdditionalPackageService additionalPackageService) {
        this.additionalPackageService = additionalPackageService;
    }


    @GetMapping("")
    public ResponseEntity<List<AdditionalPackage>> getAllAdditionalPackages() {
        return ResponseEntity.ok(additionalPackageService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AdditionalPackage> getAdditionalPackage(@PathVariable("id") long id) {
        Optional<AdditionalPackage> additionalPackage = additionalPackageService.findAdditionalPackageById(id);
        return additionalPackage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
