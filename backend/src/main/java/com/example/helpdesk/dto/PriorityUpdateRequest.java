package com.example.helpdesk.dto;

import com.example.helpdesk.model.enums.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityUpdateRequest {
    private TicketPriority priority;
}
