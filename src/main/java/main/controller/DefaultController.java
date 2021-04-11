package main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public ResponseEntity<?> hello(Model model) {
        model.addAttribute("appName", appName);
        return ResponseEntity.ok("initial setup completed");
    }
}