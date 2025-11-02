package com.wilff.gains_spring.service.interfaces;

import com.wilff.gains_spring.dto.response.ExerciseResponse;
import com.wilff.gains_spring.model.Exercise;

import java.util.List;

public interface IExerciseService {
    Exercise getById(int id);
    List<ExerciseResponse> getAll();
    int countExercisesByWorkoutId(int workoutId);
    Exercise create(Exercise exercise);
    Exercise update(int id, Exercise newExercise);
    void delete(int id);
}
