package br.api.backend.service;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.api.backend.repository.TicketsRepository;
import br.api.backend.repository.UsersRepository;
import br.api.backend.specifications.TicketSpecifications;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class TicketsService {
    @Autowired
    private TicketsRepository ticketRepository;

    @Autowired
    private UsersRepository userRepository;
    
    public Tickets saveTicket(Tickets ticket) {
        return ticketRepository.save(ticket);
    }
    
    public Page<Tickets> findByUser(Users user, Pageable pageable) {
        return ticketRepository.findByUser(user, pageable);
    }
        
    public Page<Tickets> findTicketsWithFilter(String filter, Pageable pageable) {
        Specification<Tickets> specification = TicketSpecifications.byFilter(filter);
        return ticketRepository.findAll(specification, pageable);
    }
    
    public Page<Tickets> findTicketsWithFilterUser(String filter, Users user, Pageable pageable) {
        Specification<Tickets> specification = TicketSpecifications.byFilterUser(filter, user);
        return ticketRepository.findAll(specification, pageable);
    }
    
    public Tickets updateRating(Long ticketId, int rating, String ratingDescription) throws Exception {
        System.out.println(ticketId);       
        Tickets ticket = ticketRepository.findById(ticketId).orElse(null);
        
        ticket.updateRating(rating, ratingDescription);
        return ticketRepository.save(ticket);
    }    
    
    public Tickets updateClosing(Long ticketId, Long userId, String closingDescription) throws Exception {
        System.out.println(ticketId);       
        Tickets ticket = ticketRepository.findById(ticketId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);
        
        ticket.updateClosing(user, closingDescription);
        return ticketRepository.save(ticket);
    }
}
