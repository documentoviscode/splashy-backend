package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing partner entities.
 */
@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
