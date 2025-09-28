package com.wilff.gains_spring.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkoutExerciseSetResponse {
    
    private int workoutId;
    private String workoutName;
    private LocalDateTime date;
    private List<WorkoutExerciseResponse> workoutExercises = new ArrayList<>();
    
}
