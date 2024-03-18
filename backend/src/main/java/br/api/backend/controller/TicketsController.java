package br.api.backend.controller;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import br.api.backend.paginated.PaginatedResponse;
import br.api.backend.service.TicketsService;
import br.api.backend.service.UsersService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAuthority('SCOPE_GUEST')")
@RequestMapping("/api/tickets")
public class TicketsController {
    
    @Autowired
    TicketsService ticketsService;
    @Autowired
    UsersService usersService;
    
    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")    
    public ResponseEntity<Map<String, Object>> createTicket(@RequestBody Map<String, Object> claims) {        
        System.out.println(claims);        
        Tickets ticket = new Tickets();    
        
        Users user = this.usersService.getUserById(Long.parseLong(claims.get("userId").toString()));
        
        ticket.setUser(user);
        ticket.setSubject(claims.get("subject").toString());
        ticket.setDescription(claims.get("description").toString());
        this.ticketsService.saveTicket(ticket);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(path = "/findByUser/{userId}", produces = "application/json")
    public PaginatedResponse<Tickets> findByUser(@PathVariable("userId") Long userId,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        Users user = this.usersService.getUserById(userId);
        Page<Tickets> ticketsPage = this.ticketsService.findByUser(user, PageRequest.of(page, size));
        
        return new PaginatedResponse<>(ticketsPage.getContent(), ticketsPage.getTotalPages(), ticketsPage.getTotalElements());
    }
    
    @PatchMapping("/{ticketId}/rating")
    public ResponseEntity<Tickets> updateTicketRating(@PathVariable Long ticketId,
                                                      @RequestParam int rating,
                                                      @RequestParam String ratingDescription) {
        try {
            Tickets updatedTicket = ticketsService.updateRating(ticketId, rating, ratingDescription);
            return ResponseEntity.ok(updatedTicket);
        } catch (Exception e) {
            // Implemente uma melhor gestão de exceções conforme necessário
            return ResponseEntity.badRequest().build();
        }
    }
}
