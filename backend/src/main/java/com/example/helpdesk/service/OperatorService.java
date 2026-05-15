package com.example.helpdesk.service;

import com.example.helpdesk.dto.CommentRequest;
import com.example.helpdesk.dto.CommentResponse;
import com.example.helpdesk.dto.StatusUpdateRequest;
import com.example.helpdesk.dto.TicketResponse;
import com.example.helpdesk.model.Comment;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.model.User;
import com.example.helpdesk.repository.CommentRepository;
import com.example.helpdesk.repository.TicketRepository;
import com.example.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public TicketResponse updateTicketStatus(Long id, StatusUpdateRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(request.getStatus());
        Ticket savedTicket = ticketRepository.save(ticket);

        return mapToResponse(savedTicket);
    }

    public CommentResponse addCommentToAnyTicket(Long ticketId, CommentRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

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

    private TicketResponse mapToResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCategory(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getCreatedBy().getUsername()
        );
    }
}
