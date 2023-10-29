package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.Document;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing document entities.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
