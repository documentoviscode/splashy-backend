package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role=2")
    List<User> findAllAdmins();

    @Query("SELECT u FROM User u WHERE u.role=2 AND u.id=?1")
    Optional<User> findAdminById(long id);
}
