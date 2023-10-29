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

/**
 * Controller handling operations related to partners.
 */
@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerService partnerService;

    /**
     * Constructor for the PartnerController class.
     *
     * @param partnerService Service handling partner operations.
     */
    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /**
     * Retrieve a list of all partners.
     *
     * @return ResponseEntity containing the list of partners or an error response.
     */
    @GetMapping("")
    public ResponseEntity<List<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerService.findAll());
    }

    /**
     * Retrieve a partner by a specified ID.
     *
     * @param id The ID of the partner.
     * @return ResponseEntity containing the partner or an error response if the partner is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartner(@PathVariable("id") long id) {
        Optional<Partner> partner = partnerService.findPartnerById(id);
        return partner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
