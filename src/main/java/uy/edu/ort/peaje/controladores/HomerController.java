package uy.edu.ort.peaje.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomerController {
  @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }
}
