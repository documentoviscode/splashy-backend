package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.dto.CreateSubscriptionDto;
import org.documentoviscode.splashyapi.services.SubscriptionService;
import org.documentoviscode.splashyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing subscription-related operations.
 */
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    /**
     * Constructor for the SubscriptionController class.
     *
     * @param subscriptionService The service for managing subscription-related operations.
     */
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
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

    /**
     * Create a new subscription for a specified user.
     *
     * @param subscriptionDto The subscription DTO to create Subscription Entity.
     * @param userId          The ID of the user for whom the subscription is created.
     * @return ResponseEntity containing the created subscription and HTTP status.
     */
    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody CreateSubscriptionDto subscriptionDto, @RequestParam Long userId)
    {
        Optional<User> userOptional = userService.findUserById(userId);

        if(userOptional.isPresent())
        {
            Subscription newSubscription = CreateSubscriptionDto
                    .dtoToEntityMapper()
                    .apply(subscriptionDto);

            newSubscription.setUser(userOptional.get());

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
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
