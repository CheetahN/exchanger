package main.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class Rate {

    private Currency base;
    private HashMap<Currency, Double> rates;
    private Long timestamp;
}
