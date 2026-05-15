package com.example.helpdesk.controller;

import com.example.helpdesk.dto.CommentRequest;
import com.example.helpdesk.dto.CommentResponse;
import com.example.helpdesk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long ticketId,
            @RequestBody CommentRequest request
    ) {
        CommentResponse response = commentService.addComment(ticketId, request);
        return ResponseEntity.ok(response);
    }
}
