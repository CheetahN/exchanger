package main.controller;

import main.api.response.ExchangeResponse;
import main.model.Currency;
import main.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {
    @Value("${spring.application.name}")
    private String appName;

    private final ExchangeService exchangeService;

    @Autowired
    public DefaultController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/")
    public ResponseEntity<?> hello(Model model) {
        model.addAttribute("appName", appName);
        return ResponseEntity.ok("initial setup completed");
    }

    @GetMapping("/exchange")
    public ResponseEntity<ExchangeResponse> exchange(
            @RequestParam("amount") Long amount,
            @RequestParam("user_id") Long userId,
            @RequestParam("source") Currency source,
            @RequestParam("target") Currency target) {

        return ResponseEntity.ok(exchangeService.exchange(amount, source, target, userId));
    }
}