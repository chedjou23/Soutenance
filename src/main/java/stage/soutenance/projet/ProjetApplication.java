package stage.soutenance.projet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import stage.soutenance.projet.entities.AppUser;
import stage.soutenance.projet.entities.Role;
import stage.soutenance.projet.repositories.AppUserRepository;

@SpringBootApplication
public class ProjetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetApplication.class, args);
	}

	@Bean
	public CommandLineRunner initUsers(AppUserRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (!utilisateurRepository.existsByEmail("client@gmail.com")) {
				AppUser client = new AppUser();
				client.setEmail("client@gmail.com");
				client.setPassword(passwordEncoder.encode("client")); // mot de passe brut : client
				client.setRole(Role.CLIENT);
				utilisateurRepository.save(client);
			}

			if (!utilisateurRepository.existsByEmail("gestionnaire@gmail.com")) {
				AppUser gestionnaire = new AppUser();
				gestionnaire.setEmail("gestionnaire@gmail.com");
				gestionnaire.setPassword(passwordEncoder.encode("gestionnaire")); // mot de passe brut : gestionnaire
				gestionnaire.setRole(Role.GESTIONNAIRE);
				utilisateurRepository.save(gestionnaire);
			}
		};
	}
}
