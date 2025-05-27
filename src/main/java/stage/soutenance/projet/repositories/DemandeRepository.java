package stage.soutenance.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stage.soutenance.projet.entities.Demande;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
  
}
