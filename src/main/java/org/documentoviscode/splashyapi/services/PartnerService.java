package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.Partner;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.repositories.PartnerRepository;
import org.documentoviscode.splashyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository)
    {
        this.partnerRepository = partnerRepository;
    }

    public Optional<Partner> findPartnerById(Long id) {
        return partnerRepository.findById(id);
    }

    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }
}
