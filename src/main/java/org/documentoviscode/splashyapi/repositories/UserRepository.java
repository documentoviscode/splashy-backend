package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing user entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieve a list of all administrators (admins).
     *
     * @return List of users with the role of admin.
     */
    @Query("SELECT u FROM User u WHERE u.role = 2")
    List<User> findAllAdmins();

    /**
     * Retrieve an administrator (admin) by a specified ID.
     *
     * @param id The ID of the administrator (admin).
     * @return Optional containing the admin or an empty optional if the admin is not found.
     */
    @Query("SELECT u FROM User u WHERE u.role = 2 AND u.id = ?1")
    Optional<User> findAdminById(long id);
}
