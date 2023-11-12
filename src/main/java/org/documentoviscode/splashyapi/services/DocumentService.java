package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.Document;
import org.documentoviscode.splashyapi.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing document-related operations.
 */

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    /**
     * Constructor for the DocumentService class.
     *
     * @param documentRepository The repository for managing document entities.
     */
    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Retrieve a document by a specified ID.
     *
     * @param id The ID of the document.
     * @return Optional containing the document or an empty optional if the document is not found.
     */
    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    /**
     * Retrieve a list of all documents.
     *
     * @return List of all document entities.
     */
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    /**
     * Add a new document to the repository.
     *
     * @param document The document to be added.
     * @return The added document.
     */
    public Document create(Document document) {
        return documentRepository.save(document);
    }

}
