package br.api.backend.service;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.api.backend.repository.TicketsRepository;
import java.util.List;

@Service
public class TicketsService {
    @Autowired
    private TicketsRepository ticketRepository;
    
    public Tickets saveTicket(Tickets ticket) {
        return ticketRepository.save(ticket);
    }
    
    public List<Tickets> findByUser(Users user) {
        return ticketRepository.findByUser(user);
    }    
}
