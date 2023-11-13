package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.domain.Document;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.repositories.DocumentRepository;
import org.documentoviscode.splashyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing document-related operations.
 */

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for the DocumentService class.
     *
     * @param documentRepository The repository for managing document entities.
     */
    @Autowired
    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
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
     * Creates and adds a new document to the repository.
     *
     * @param userId      The ID of the user who owns the document.
     * @param GDriveId    The Google Drive ID of the document.
     * @param type        The format/type of the document.
     * @return            The added document.
     * @throws IllegalArgumentException if the specified user is not found.
     */
    public Document create(Long userId, String GDriveId, DocFormat type) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Document newDocument = Document.builder()
                .type(type)
                .GDriveLink(GDriveId)
                .creationDate(LocalDate.now())
                .user(user)
                .build();

        return documentRepository.save(newDocument);
    }

}
