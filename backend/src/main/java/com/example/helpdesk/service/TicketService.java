package com.example.helpdesk.service;

import com.example.helpdesk.dto.TicketCreateRequest;
import com.example.helpdesk.dto.TicketResponse;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.model.User;
import com.example.helpdesk.model.enums.TicketPriority;
import com.example.helpdesk.model.enums.TicketStatus;
import com.example.helpdesk.repository.TicketRepository;
import com.example.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public TicketResponse createTicket(TicketCreateRequest request) {
        User currentUser = getCurrentUser();

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(TicketPriority.MEDIUM); // Default priority come da richiesta
        ticket.setStatus(TicketStatus.OPEN); // Default status
        ticket.setCreatedBy(currentUser);

        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToResponse(savedTicket);
    }

    public List<TicketResponse> getMyTickets() {
        User currentUser = getCurrentUser();
        List<Ticket> tickets = ticketRepository.findByCreatedBy(currentUser);
        return tickets.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public TicketResponse getTicketDetails(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

        // Controllo ownership
        if (!ticket.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access Denied: Ticket does not belong to the current user");
        }

        return mapToResponse(ticket);
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
