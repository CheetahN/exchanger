package main.service;

import main.api.response.ExchangeResponse;
import main.model.Currency;
import main.model.Exchange;
import main.model.ExchangeRate;
import main.repository.ExchangeRateRepository;
import main.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ExchangeService {
    @Value("${currency.rate.cache.minutes}")
    private Integer cacheTime;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeService(ExchangeRepository exchangeRepository, ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRepository = exchangeRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeResponse exchange(Long amount, Currency original, Currency target, Long userId) {

        ExchangeRate exchangeRate = exchangeRateRepository.findByOriginalAndTarget(original, target).orElse(new ExchangeRate(original, target));
        if (exchangeRate.getRate() == null || Duration.between(exchangeRate.getDate(), LocalDateTime.now()).toMinutes() > cacheTime ) {
            exchangeRate.setRate(0.7); //TODO
            exchangeRate.setDate(LocalDateTime.now());
            exchangeRateRepository.save(exchangeRate);
        }

        Exchange exchange = exchangeRepository.save(Exchange.builder()
                .date(LocalDateTime.now())
                .amountOriginal(amount)
                .amountTarget(amount * exchangeRate.getRate())
                .original(original)
                .target(target)
                .userId(userId)
                .build()
        );

        return new ExchangeResponse(exchange.getId(), exchange.getAmountTarget());
    }
}
