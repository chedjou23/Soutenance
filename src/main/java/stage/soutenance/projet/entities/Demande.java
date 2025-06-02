package stage.soutenance.projet.entities;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Demande {
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant dateSoumission;

  private Statut statut; 
}

