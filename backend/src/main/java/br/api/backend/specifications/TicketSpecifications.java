package br.api.backend.specifications;

import br.api.backend.model.Tickets;
import br.api.backend.model.Users;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.ArrayList;

public class TicketSpecifications {

    public static Specification<Tickets> byFilter(String filter) {
        return (Root<Tickets> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (filter == null || filter.isEmpty()) {
                return cb.isTrue(cb.literal(true));
            }            
            Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), "%" + filter.toLowerCase() + "%");
            Predicate idPredicate = cb.conjunction();
            try {
                Long id = Long.parseLong(filter);
                idPredicate = cb.equal(root.get("id"), id);
            } catch (NumberFormatException e) {                
            }
            return cb.or(descriptionPredicate, idPredicate);
        };
    }    

    public static Specification<Tickets> byFilterUser(String filter, Users user) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();            
            if (user != null) {
                predicates.add(cb.equal(root.get("user"), user));
            }
            if (filter != null && !filter.isEmpty()) {
                Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), "%" + filter.toLowerCase() + "%");
                
                Predicate idPredicate = cb.conjunction();
                try {
                    Long id = Long.parseLong(filter);
                    idPredicate = cb.equal(root.get("id"), id);
                } catch (NumberFormatException e) {                    
                }
                
                predicates.add(cb.or(descriptionPredicate, idPredicate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

