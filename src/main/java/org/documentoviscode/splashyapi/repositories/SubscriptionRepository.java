package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing subscription entities.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
