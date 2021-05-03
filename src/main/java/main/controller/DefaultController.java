package main.controller;

import lombok.extern.log4j.Log4j2;
import main.api.response.ExchangeResponse;
import main.api.response.ResultResponse;
import main.model.Currency;
import main.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class DefaultController {

    private final ExchangeService exchangeService;

    @Autowired
    public DefaultController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @GetMapping("/exchange")
    public ResponseEntity<ExchangeResponse> exchange(
            @RequestParam("amount") Long amount,
            @RequestParam("user_id") Long userId,
            @RequestParam("source") Currency source,
            @RequestParam("target") Currency target) {

        log.info(String.format("User %s requested exchanging %s %s to %s", userId, amount, source, target));
        return ResponseEntity.ok(exchangeService.exchange(amount, source, target, userId));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStatistics(@RequestParam("mode") String mode,
                                           @RequestParam(name = "value", required = false) Long value) {
        switch (mode) {
            case "popular": {
                log.info("most popular transactions requested");
                return ResponseEntity.ok(exchangeService.getPopularRequests());
            }
            case "over": {
                log.info("statistics for over the limit requested");
                return ResponseEntity.ok(exchangeService.getAmountOver(value));
            }
            case "total": {
                log.info("statistics for total over the limit requested");
                return ResponseEntity.ok(exchangeService.getTotalAmountOver(value));
            }
            default: {
                log.warn("incorrect statistics mode requested");
                return ResponseEntity.ok(new ResultResponse("incorrect mode"));
            }
        }
    }
}