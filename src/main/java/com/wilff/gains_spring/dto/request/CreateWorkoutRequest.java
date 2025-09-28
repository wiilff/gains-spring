package com.wilff.gains_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWorkoutRequest {
    
    @NotBlank(message = "Workout name is required")
    private String name;

    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @JsonProperty("isShared")
    private boolean shared;
    private String note;
}
