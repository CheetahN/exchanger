package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TopDirectionListResponse {
    private List<DirectionResponse> directions;
}
