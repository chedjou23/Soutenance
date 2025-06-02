package stage.soutenance.projet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }


    @GetMapping("/default")
    public String defaultPage() {
        return "default"; 
    }
}
