package main.service;

import main.api.response.ExchangeResponse;
import main.model.Currency;
import main.model.Exchange;
import main.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final RateService rateService;

    @Autowired
    public ExchangeService(ExchangeRepository exchangeRepository, RateService rateService) {
        this.exchangeRepository = exchangeRepository;
        this.rateService = rateService;
    }

    public ExchangeResponse exchange(Long amount, Currency source, Currency target, Long userId) {

        Exchange exchange = exchangeRepository.save(Exchange.builder()
                .date(LocalDateTime.now())
                .amountSource(amount)
                .amountTarget(amount * rateService.getRate(source, target))
                .source(source)
                .target(target)
                .userId(userId)
                .build()
        );

        return new ExchangeResponse(exchange.getId(), exchange.getAmountTarget());
    }
}
