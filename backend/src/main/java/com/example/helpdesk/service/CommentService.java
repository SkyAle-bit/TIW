package com.example.helpdesk.service;

import com.example.helpdesk.dto.CommentRequest;
import com.example.helpdesk.dto.CommentResponse;
import com.example.helpdesk.model.Comment;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.model.User;
import com.example.helpdesk.repository.CommentRepository;
import com.example.helpdesk.repository.TicketRepository;
import com.example.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CommentResponse addComment(Long ticketId, CommentRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

        // Check ownership per gli utenti (livello base)
        if (!ticket.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access Denied: Ticket does not belong to the current user");
        }

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setTicket(ticket);
        comment.setAuthor(currentUser);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(
                savedComment.getId(),
                savedComment.getText(),
                savedComment.getAuthor().getUsername(),
                savedComment.getCreatedAt()
        );
    }
}
