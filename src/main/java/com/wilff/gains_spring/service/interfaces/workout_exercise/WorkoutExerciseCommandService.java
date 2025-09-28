package com.wilff.gains_spring.service.interfaces.workout_exercise;

import com.wilff.gains_spring.model.WorkoutExercise;

public interface WorkoutExerciseCommandService {
    WorkoutExercise create(int workoutId, int exerciseId);
    void delete(int workoutExerciseId);
}
