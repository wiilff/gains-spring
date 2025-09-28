package com.wilff.gains_spring.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileData {
    
    private LocalDateTime date;
    private double minutes;
}
