package com.example.helpdesk.controller;

import com.example.helpdesk.dto.CommentRequest;
import com.example.helpdesk.dto.CommentResponse;
import com.example.helpdesk.dto.StatusUpdateRequest;
import com.example.helpdesk.dto.TicketResponse;
import com.example.helpdesk.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operator")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> tickets = operatorService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/tickets/{id}/status")
    public ResponseEntity<TicketResponse> updateTicketStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request
    ) {
        TicketResponse response = operatorService.updateTicketStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/tickets/{id}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequest request
    ) {
        CommentResponse response = operatorService.addCommentToAnyTicket(id, request);
        return ResponseEntity.ok(response);
    }
}
