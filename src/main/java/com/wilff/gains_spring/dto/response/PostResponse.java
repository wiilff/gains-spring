package com.wilff.gains_spring.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    
    private int id;
    private String authorName;
    private String authorEmail;
    private String content;
    private LocalDateTime createdAt;
    private Integer workoutId;
    private String workoutName;

}
