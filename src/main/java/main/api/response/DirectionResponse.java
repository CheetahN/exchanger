package main.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectionResponse {
    private String target;
    private String source;
    private Long count;
}
