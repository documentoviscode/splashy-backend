package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing subscription-related operations.
 */
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    /**
     * Constructor for the SubscriptionController class.
     *
     * @param subscriptionService The service for managing subscription-related operations.
     */
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Retrieve a list of all subscriptions.
     *
     * @return ResponseEntity containing the list of all subscription entities.
     */
    @GetMapping("")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.findAll());
    }

    /**
     * Retrieve a subscription by a specified ID.
     *
     * @param id The ID of the subscription to retrieve.
     * @return ResponseEntity containing the subscription or a "not found" response if the subscription is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("id") long id) {
        Optional<Subscription> subscription = subscriptionService.findSubscriptionById(id);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription newSubscription )
    {
        Subscription createdSubscription = subscriptionService.create(newSubscription);

        if(createdSubscription!=null)
        {
            return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
