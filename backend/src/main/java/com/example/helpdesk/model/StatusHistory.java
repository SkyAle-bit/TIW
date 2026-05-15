package com.example.helpdesk.model;

import com.example.helpdesk.model.enums.TicketStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private TicketStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private TicketStatus newStatus;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @CreationTimestamp
    private LocalDateTime changedAt;
}
