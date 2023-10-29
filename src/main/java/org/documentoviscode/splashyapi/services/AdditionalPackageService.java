package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.repositories.AdditionalPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdditionalPackageService {

    private final AdditionalPackageRepository additionalPackageRepository;


    @Autowired
    public AdditionalPackageService(AdditionalPackageRepository additionalPackageRepository) {
        this.additionalPackageRepository = additionalPackageRepository;
    }


    public Optional<AdditionalPackage> findAdditionalPackageById(Long id) {
        return additionalPackageRepository.findById(id);
    }


    public List<AdditionalPackage> findAll() {
        return additionalPackageRepository.findAll();
    }
}
