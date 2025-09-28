package com.wilff.gains_spring.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.wilff.gains_spring.model.LiftingSet;

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
public class WorkoutExerciseResponse {

    private int workoutExerciseId;
    private int order;
    private int exerciseId;
    private String name;
    private List<SetResponse> sets = new ArrayList<>();
}
