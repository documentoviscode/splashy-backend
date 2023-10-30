package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing AdditionalPackage entities.
 */
@Repository
public interface AdditionalPackageRepository extends JpaRepository<AdditionalPackage, Long> {
}
