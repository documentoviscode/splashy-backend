package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.data.requests.SubscriptionDTO;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing subscription-related operations.
 */
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Constructor for the SubscriptionService class.
     *
     * @param subscriptionRepository The repository for managing subscription entities.
     */
    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Retrieve a subscription by a specified ID.
     *
     * @param id The ID of the subscription.
     * @return Optional containing the subscription or an empty optional if the subscription is not found.
     */
    public Optional<Subscription> findSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    /**
     * Retrieve a list of all subscriptions.
     *
     * @return List of all subscription entities.
     */
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    /**
     * Update an existing subscription.
     *
     * @param id             The ID of the subscription to be updated.
     * @param updatedSubscription The updated subscription data.
     * @return The updated subscription or null if the subscription with the specified ID is not found.
     */
    public Subscription updateSubscription(Long id, SubscriptionDTO updatedSubscription) {
        return findSubscriptionById(id)
                .map(subscriptionToUpdate -> {
                    subscriptionToUpdate.setType(updatedSubscription.type());
                    subscriptionToUpdate.setGDriveLink(updatedSubscription.GDriveLink());
                    subscriptionToUpdate.setCreationDate(updatedSubscription.creationDate());
                    subscriptionToUpdate.setStartDate(updatedSubscription.startDate());
                    subscriptionToUpdate.setPeriod(updatedSubscription.period());
                    subscriptionToUpdate.setMonthlyRate(updatedSubscription.monthlyRate());
                    return subscriptionRepository.save(subscriptionToUpdate);
                })
                .orElse(null);
    }
}
