package br.api.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String status = "OPEN";
}