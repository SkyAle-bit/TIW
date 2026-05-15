package com.example.helpdesk.dto;

import com.example.helpdesk.model.enums.TicketCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateRequest {
    private String title;
    private String description;
    private TicketCategory category;
}
