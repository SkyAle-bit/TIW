package com.example.helpdesk.dto;

import com.example.helpdesk.model.enums.TicketCategory;
import com.example.helpdesk.model.enums.TicketPriority;
import com.example.helpdesk.model.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private TicketCategory category;
    private TicketPriority priority;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
}
