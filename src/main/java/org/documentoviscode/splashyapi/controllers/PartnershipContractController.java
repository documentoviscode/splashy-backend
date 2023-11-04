package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.services.PartnershipContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing partnership contract-related operations.
 */
@RestController
@RequestMapping("/partnershipContracts")
public class PartnershipContractController {
    private final PartnershipContractService partnershipContractService;

    /**
     * Constructor for the PartnershipContractController class.
     *
     * @param partnershipContractService The service for managing partnership contract-related operations.
     */
    @Autowired
    public PartnershipContractController(PartnershipContractService partnershipContractService) {
        this.partnershipContractService = partnershipContractService;
    }

    /**
     * Retrieve a list of all partnership contracts.
     *
     * @return ResponseEntity containing the list of all partnership contract entities.
     */
    @GetMapping("")
    public ResponseEntity<List<PartnershipContract>> getAllPartnershipContracts() {
        return ResponseEntity.ok(partnershipContractService.findAll());
    }

    /**
     * Retrieve a partnership contract by a specified ID.
     *
     * @param id The ID of the partnership contract to retrieve.
     * @return ResponseEntity containing the partnership contract or a "not found" response if the contract is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartnershipContract> getPartnershipContract(@PathVariable("id") long id) {
        Optional<PartnershipContract> partnershipContract = partnershipContractService.findPartnershipContractById(id);
        return partnershipContract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
