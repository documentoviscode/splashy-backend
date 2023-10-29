package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller handling operations related to users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for the UserController class.
     *
     * @param userService Service handling user operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieve a list of all users.
     *
     * @return ResponseEntity containing the list of users or an error response.
     */
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Retrieve a user by a specified ID.
     *
     * @param id The ID of the user.
     * @return ResponseEntity containing the user or an error response if the user is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve a list of all administrators (admins).
     *
     * @return ResponseEntity containing the list of administrators (admins) or an error response.
     */
    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userService.findAllAdmins());
    }

    /**
     * Retrieve an administrator (admin) by a specified ID.
     *
     * @param id The ID of the administrator (admin).
     * @return ResponseEntity containing the administrator (admin) or an error response if the administrator is not found.
     */
    @GetMapping("/admins/{id}")
    public ResponseEntity<User> getAdmin(@PathVariable("id") long id) {
        Optional<User> user = userService.findAdminById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

