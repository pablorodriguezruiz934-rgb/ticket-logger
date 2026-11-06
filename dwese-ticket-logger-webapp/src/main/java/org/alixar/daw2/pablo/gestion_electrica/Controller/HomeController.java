package org.alixar.daw2.pablo.gestion_electrica.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
}

