package org.documentoviscode.splashyapi.services;


import org.documentoviscode.splashyapi.domain.Client;
import org.documentoviscode.splashyapi.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing client-related operations.
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Constructor for the ClientService class.
     *
     * @param clientRepository The repository for managing client entities.
     */
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Retrieve a client by a specified ID.
     *
     * @param id The ID of the client.
     * @return Optional containing the client or an empty optional if the client is not found.
     */
    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    /**
     * Retrieve a list of all clients.
     *
     * @return List of all client entities.
     */
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
