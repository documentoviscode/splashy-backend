package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.Partner;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.services.PartnerService;
import org.documentoviscode.splashyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Partner> getPartner(@PathVariable("id") long id) {
        Optional<Partner> partner = partnerService.findPartnerById(id);
        return partner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
