package com.wilff.gains_spring.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserExerciseOverallStats {
    
    private int exerciseId;
    private String exerciseName;
    private Long setCount;
    private Long workoutCount;

    public UserExerciseOverallStats(int exerciseId, String exerciseName, Long setCount, Long workoutCount) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.setCount = setCount;
        this.workoutCount = workoutCount;
    }
}
