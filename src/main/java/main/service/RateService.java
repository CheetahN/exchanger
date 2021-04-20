package main.service;

import com.google.gson.Gson;
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
                e.printStackTrace();
            }
        }

        return rates.getRates().get(target) / rates.getRates().get(source);
    }

    private void updateRates() throws IOException, InterruptedException {
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
    }
}
