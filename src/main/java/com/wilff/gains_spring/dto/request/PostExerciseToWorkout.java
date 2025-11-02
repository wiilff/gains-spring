package com.wilff.gains_spring.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostExerciseToWorkout {
    
    private int exercise_id;
    private int workout_id;
    private int order;
}
