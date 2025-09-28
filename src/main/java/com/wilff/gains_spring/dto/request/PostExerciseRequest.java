package com.wilff.gains_spring.dto.request;

import com.wilff.gains_spring.model.enums.MuscleGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostExerciseRequest {

    @NotBlank(message = "Exercise name is required")
    private String name;
    @NotNull(message = "Muscle group is required")
    private MuscleGroup muscleGroup;
}
