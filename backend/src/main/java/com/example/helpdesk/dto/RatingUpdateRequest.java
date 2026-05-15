package com.example.helpdesk.dto;

import com.example.helpdesk.model.enums.TicketRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingUpdateRequest {
    private TicketRating rating;
}
