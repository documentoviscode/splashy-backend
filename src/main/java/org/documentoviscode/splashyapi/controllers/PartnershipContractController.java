package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.data.requests.PartnershipContractDTO;
import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.dto.CreatePartnershipContractDto;
import org.documentoviscode.splashyapi.dto.CreateSubscriptionDto;
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

    /**
     * Create a new PartnershipContract for a specified user.
     *
     * @param partnershipContractDto The partnership contract DTO to create partnership contract entity.
     * @param userId          The ID of the user for whom the partnership contract is created.
     * @return ResponseEntity containing the created partnership contract and HTTP status.
     */
    @PostMapping
    public ResponseEntity<PartnershipContract> createPartnershipContract(@RequestBody CreatePartnershipContractDto partnershipContractDto, @RequestParam Long userId )
    {
        Optional<User> userOptional = userService.findUserById(userId);

        if(userOptional.isPresent())
        {
            PartnershipContract newPartnershipContract = CreatePartnershipContractDto
                    .dtoToEntityMapper()
                    .apply(partnershipContractDto);

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

    /**
     * Update an existing partnership contract based on its ID.
     *
     * @param id                      The ID of the partnership contract to be updated.
     * @param updatedPartnershipContract The updated partnership contract data.
     * @return A ResponseEntity containing the updated partnership contract if successful, or a not found status if the contract with the specified ID is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<PartnershipContract> updatePartnershipContract(@PathVariable Long id, @RequestBody PartnershipContractDTO updatedPartnershipContract) {
        PartnershipContract updated = partnershipContractService.updatePartnershipContract(id, updatedPartnershipContract);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
