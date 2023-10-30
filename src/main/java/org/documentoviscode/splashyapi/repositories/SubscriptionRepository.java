package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing subscription entities.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
