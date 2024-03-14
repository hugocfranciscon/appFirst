package br.api.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.api.backend.repository.TicketsRepository;

@Service
public class TicketsService {
    @Autowired
    private TicketsRepository ticketRepository;
    
}
