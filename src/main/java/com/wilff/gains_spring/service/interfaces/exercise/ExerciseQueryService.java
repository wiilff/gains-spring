package com.wilff.gains_spring.service.interfaces.exercise;

import java.util.List;

import com.wilff.gains_spring.dto.response.ExerciseResponse;
import com.wilff.gains_spring.model.Exercise;

public interface ExerciseQueryService {
    Exercise getById(int id);
    List<ExerciseResponse> getAll();
    int countExercisesByWorkoutId(int workoutId);
}
