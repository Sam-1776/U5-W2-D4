package samuelesimeone.esercizio_u5w2d4.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samuelesimeone.esercizio_u5w2d4.entities.Autore;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutoriDAO extends JpaRepository<Autore, UUID> {
    Optional<Autore> findByEmail(String email);
}
