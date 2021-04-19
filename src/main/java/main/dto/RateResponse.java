package main.dto;

import lombok.Data;
import main.model.Currency;

import java.util.HashMap;

@Data
public class RateResponse {

    private Currency base;
    HashMap<Currency, Double> rates;
}
