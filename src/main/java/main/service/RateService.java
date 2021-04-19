package main.service;

import main.model.Currency;
import main.model.Rate;
import main.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RateService {
    @Value("${currency.rate.cache.minutes}")
    private Integer cacheTime;
    @Value("${fixer.access.key}")
    private String accessKey;


    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Double getRate(Currency source, Currency target) {
        Optional<Rate> rate = rateRepository.findByOriginalAndTarget(source, target);
        if (rate.isEmpty() || Duration.between(rate.get().getDate(), LocalDateTime.now()).toMinutes() > cacheTime) {
            try {
                updateRates();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            rate = rateRepository.findByOriginalAndTarget(source, target);
        }
        return rate.orElseThrow(() -> new NullPointerException("rate request failed")).getRate();
    }

    private void updateRates() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("http://data.fixer.io/api/latest" +
                        "?access_key=" + accessKey +
                        "&symbols=GBP,RUR,EUR,USD"))
                .header("accept", "application/json")
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
