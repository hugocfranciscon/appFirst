package br.api.backend.repository;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {
    List<Tickets> findByUser(Users user);
}