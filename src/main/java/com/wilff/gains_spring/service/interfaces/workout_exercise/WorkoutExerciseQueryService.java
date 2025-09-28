package com.wilff.gains_spring.service.interfaces.workout_exercise;

import java.util.List;

import com.wilff.gains_spring.dto.response.UserExerciseOverallStats;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.model.WorkoutExercise;

public interface WorkoutExerciseQueryService {
    List<WorkoutExercise> getAllByWorkout(Workout workout);
    List<UserExerciseOverallStats> getWorkoutExerciseStats(int userId);
    List<WorkoutExercise> findByWorkoutUserIdAndExerciseId(int userId, int exerciseId);
    List<WorkoutExercise> findByWorkoutUserId(int userId);
}