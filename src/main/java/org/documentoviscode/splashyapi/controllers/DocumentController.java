package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.Document;
import org.documentoviscode.splashyapi.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing document-related operations.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    /**
     * Constructor for the DocumentController class.
     *
     * @param documentService The service for managing document-related operations.
     */
    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Retrieve a list of all documents.
     *
     * @return ResponseEntity containing the list of all document entities.
     */
    @GetMapping("")
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAll());
    }

    /**
     * Retrieve a document by a specified ID.
     *
     * @param id The ID of the document to retrieve.
     * @return ResponseEntity containing the document or a "not found" response if the document is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable("id") long id) {
        Optional<Document> document = documentService.findDocumentById(id);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
