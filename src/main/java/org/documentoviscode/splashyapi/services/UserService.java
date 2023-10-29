package org.documentoviscode.splashyapi.services;


import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructor for the UserService class.
     *
     * @param userRepository The repository for managing user entities.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve a user by a specified ID.
     *
     * @param id The ID of the user.
     * @return Optional containing the user or an empty optional if the user is not found.
     */
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieve a list of all users.
     *
     * @return List of all user entities.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieve a list of all administrators (admins).
     *
     * @return List of users with the role of admin.
     */
    public List<User> findAllAdmins() {
        return userRepository.findAllAdmins();
    }

    /**
     * Retrieve an administrator (admin) by a specified ID.
     *
     * @param id The ID of the administrator (admin).
     * @return Optional containing the admin or an empty optional if the admin is not found.
     */
    public Optional<User> findAdminById(Long id) {
        return userRepository.findAdminById(id);
    }
}
