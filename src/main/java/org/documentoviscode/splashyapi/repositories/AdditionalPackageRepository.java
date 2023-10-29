package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing AdditionalPackage entities.
 */
public interface AdditionalPackageRepository extends JpaRepository<AdditionalPackage, Long> {
}
