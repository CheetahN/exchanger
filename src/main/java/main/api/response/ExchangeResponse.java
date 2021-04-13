package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeResponse {
    private Long id;
    private Double result;
}
