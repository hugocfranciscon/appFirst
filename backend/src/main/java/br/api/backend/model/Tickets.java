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
    private String status;
    
    private int rating;
    
    @Column(columnDefinition = "TEXT")
    private String ratingDescription;
    
    @Column(columnDefinition = "TEXT")
    private String closingDescription;
    
    @ManyToOne
    @JoinColumn(name = "closing_user_id")
    private Users closingUser;
    
    public void updateRating(int rating, String ratingDescription) {
        this.rating = rating;
        this.ratingDescription = ratingDescription;
    }
    
    public void updateClosing(Users user, String closingDescription, String status) {
        this.closingUser = user;
        this.closingDescription = closingDescription;
        this.status = status;
    }
    
}
