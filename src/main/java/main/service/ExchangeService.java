package main.service;

import lombok.extern.log4j.Log4j2;
import main.api.response.ExchangeResponse;
import main.api.response.ResultResponse;
import main.model.Currency;
import main.model.Exchange;
import main.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
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
        log.trace(String.format("Writing operation %d", exchange.getId()));
        return new ExchangeResponse(exchange.getId(), exchange.getAmountTarget());
    }


    public ResultResponse getOver(Long amount) {
        ResultResponse result;
        List<Long> users = exchangeRepository.findOver(amount);
        if (!users.isEmpty()) {
            result = new ResultResponse("ok", users);
        } else {
            result = new ResultResponse("such users not found");
        }
        return result;
    }
}
