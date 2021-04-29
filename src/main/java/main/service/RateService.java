package main.service;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import main.model.Currency;
import main.model.Rate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

@Service
@Log4j2
public class RateService {
    @Value("${currency.rate.cache.hours}")
    private Integer cacheTime;
    @Value("${fixer.access.key}")
    private String accessKey;

    private static Rate rates;

    public Double getRate(Currency source, Currency target) {
        if (rates == null || Instant.now().getEpochSecond() - rates.getTimestamp() > (cacheTime * 3600)) {
            try {
                updateRates();
            } catch (IOException | InterruptedException e) {
                log.error("Updating rates failed");
                log.trace(e.toString());
            }
        }

        return rates.getRates().get(target) / rates.getRates().get(source);
    }

    private void updateRates() throws IOException, InterruptedException {
        log.info("Requesting rates update");
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("http://data.fixer.io/api/latest" +
                        "?access_key=" + accessKey +
                        "&symbols=EUR,RUB,USD,GBP,JPY,CHF,CNY,TRY"))
                .header("accept", "application/json")
                .build();
        rates = new Gson().fromJson(
                client.send(request, HttpResponse.BodyHandlers.ofString())
                        .body(),
                Rate.class);
        log.trace(String.format("Current rates: %s", rates));
    }
}
