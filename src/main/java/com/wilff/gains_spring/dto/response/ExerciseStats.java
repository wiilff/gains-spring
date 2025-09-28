package com.wilff.gains_spring.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.wilff.gains_spring.model.enums.MuscleGroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseStats {

    private String exerciseName;
    private MuscleGroup muscleGroup;
    private LocalDateTime date;
    private List<SetResponse> sets;

}
