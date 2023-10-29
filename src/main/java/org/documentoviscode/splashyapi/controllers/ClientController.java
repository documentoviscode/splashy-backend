package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.Client;
import org.documentoviscode.splashyapi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller handling operations related to clients.
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    /**
     * Constructor for the ClientController class.
     *
     * @param clientService Service handling client operations.
     */
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Retrieve a list of all clients.
     *
     * @return ResponseEntity containing the list of clients or an error response.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    /**
     * Retrieve a client by a specified ID.
     *
     * @param id The ID of the client.
     * @return ResponseEntity containing the client or an error response if the client is not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") long id) {
        Optional<Client> client = clientService.findClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
