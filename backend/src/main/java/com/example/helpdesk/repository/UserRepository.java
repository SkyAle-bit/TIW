package com.example.helpdesk.repository;

import com.example.helpdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.role = 'OPERATOR' ORDER BY (SELECT COUNT(t) FROM Ticket t WHERE t.assignedTo = u AND t.status IN ('OPEN', 'IN_PROGRESS')) ASC LIMIT 1")
    Optional<User> findOperatorWithLeastOpenOrInProgressTickets();
}
