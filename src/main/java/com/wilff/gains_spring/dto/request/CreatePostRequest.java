package com.wilff.gains_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRequest {
    
    @NotBlank(message = "Content is required")
    private String content;
    private int workoutId;

}
