package com.wilff.gains_spring.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetResponse {
    private int id;
    private double reps;
    private double weight;
    private int order;
    private Instant loggedAt;
}
