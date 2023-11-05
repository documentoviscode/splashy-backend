package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.services.PartnershipContractService;
import org.documentoviscode.splashyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing partnership contract-related operations.
 */
@RestController
@RequestMapping("/partnershipContracts")
public class PartnershipContractController {
    private final PartnershipContractService partnershipContractService;
    private final UserService userService;

    /**
     * Constructor for the PartnershipContractController class.
     *
     * @param partnershipContractService The service for managing partnership contract-related operations.
     */
    @Autowired
    public PartnershipContractController(PartnershipContractService partnershipContractService, UserService userService) {
        this.partnershipContractService = partnershipContractService;
        this.userService = userService;
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

    @PostMapping
    public ResponseEntity<PartnershipContract> createPartnershipContract(@RequestBody PartnershipContract newPartnershipContract, @RequestParam Long userId )
    {
        Optional<User> userOptional = userService.findUserById(userId);

        if(userOptional.isPresent())
        {
            newPartnershipContract.setUser(userOptional.get());
            PartnershipContract createdPartnershipContract = partnershipContractService.create(newPartnershipContract);

            if(createdPartnershipContract!=null)
            {
                return new ResponseEntity<>(createdPartnershipContract, HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
