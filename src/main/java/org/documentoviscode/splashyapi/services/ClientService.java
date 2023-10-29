package org.documentoviscode.splashyapi.services;


import org.documentoviscode.splashyapi.domain.Client;
import org.documentoviscode.splashyapi.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    
}
