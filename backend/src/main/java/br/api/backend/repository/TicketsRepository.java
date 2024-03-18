package br.api.backend.repository;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {
    Page<Tickets> findByUser(Users user, Pageable pageable);
}