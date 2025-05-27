package stage.soutenance.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stage.soutenance.projet.entities.DocumentClient;

import java.util.List;

@Repository
public interface DocumentClientRepository extends JpaRepository<DocumentClient, Long> {
  List<DocumentClient> findByDemandeId(Long demandeId);
}

