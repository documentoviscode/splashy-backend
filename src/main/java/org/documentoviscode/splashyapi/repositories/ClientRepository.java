package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.Client;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
