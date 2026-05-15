package com.example.helpdesk.repository;

import com.example.helpdesk.model.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
}
