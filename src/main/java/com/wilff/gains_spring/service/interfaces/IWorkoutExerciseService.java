package com.wilff.gains_spring.service.interfaces;

import com.wilff.gains_spring.dto.response.UserExerciseOverallStats;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.model.WorkoutExercise;

import java.util.List;

public interface IWorkoutExerciseService {
    List<WorkoutExercise> getAllByWorkout(Workout workout);
    List<UserExerciseOverallStats> getWorkoutExerciseStats(int userId);
    List<WorkoutExercise> findByWorkoutUserIdAndExerciseId(int userId, int exerciseId);
    List<WorkoutExercise> findByWorkoutUserId(int userId);
    WorkoutExercise create(int workoutId, int exerciseId, int order);
    void delete(int workoutExerciseId);

    int countTotalWorkoutExercises(int userId);
}
