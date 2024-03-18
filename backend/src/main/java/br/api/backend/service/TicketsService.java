package br.api.backend.service;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.api.backend.repository.TicketsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TicketsService {
    @Autowired
    private TicketsRepository ticketRepository;
    
    public Tickets saveTicket(Tickets ticket) {
        return ticketRepository.save(ticket);
    }
    
    public Page<Tickets> findByUser(Users user, Pageable pageable) {
        return ticketRepository.findByUser(user, pageable);
    }
    
    public Tickets updateRating(Long ticketId, int rating, String ratingDescription) throws Exception {
        Tickets ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new Exception("Ticket não encontrado."));
        ticket.updateRating(rating, ratingDescription);
        return ticketRepository.save(ticket);
    }
}
