package com.wilff.gains_spring.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostSetRequest {
    
    private Integer id;
    private Integer order;
    private Double reps;
    private Double weight;
    private LocalDateTime loggedAt;

}
