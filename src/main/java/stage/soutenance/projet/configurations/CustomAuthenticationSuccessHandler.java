package stage.soutenance.projet.configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import stage.soutenance.projet.entities.AppUser;
import stage.soutenance.projet.repositories.AppUserRepository;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String email = authentication.getName();

        AppUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Long userId = user.getId();

        switch (user.getRole()) {
            case CLIENT:
                response.sendRedirect("/clients/" + userId + "/upload");
                break;
            case GESTIONNAIRE:
                response.sendRedirect("/gestionnaires/demandes/" + userId);
                break;
            default:
                response.sendRedirect("/default");
                break;
        }
    }
}
