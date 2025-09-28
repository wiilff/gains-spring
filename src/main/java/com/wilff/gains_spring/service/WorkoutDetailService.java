package com.wilff.gains_spring.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import com.wilff.gains_spring.dto.response.ExerciseStats;
import com.wilff.gains_spring.dto.response.SetResponse;
import com.wilff.gains_spring.dto.response.WorkoutExerciseResponse;
import com.wilff.gains_spring.dto.response.WorkoutExerciseSetResponse;
import com.wilff.gains_spring.service.impl.SetService;
import com.wilff.gains_spring.service.impl.WorkoutExerciseServiceImpl;
import com.wilff.gains_spring.service.impl.WorkoutServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WorkoutDetailService {

    private final WorkoutServiceImpl workoutService;
    private final WorkoutExerciseServiceImpl workoutExerciseService;
    private final SetService setService;

    public WorkoutExerciseSetResponse getWorkoutWithDetails(int workoutId) {
        var workout = workoutService.getById(workoutId);

        var exercises = workoutExerciseService.getAllByWorkout(workout).stream()
                .map(ex -> WorkoutExerciseResponse.builder()
                        .workoutExerciseId(ex.getId())
                        .exerciseId(ex.getExercise().getId())
                        .name(ex.getExercise().getName())
                        .sets(setService.getByWorkoutExercise(ex).stream()
                                .map(set -> SetResponse.builder()
                                        .id(set.getId())
                                        .reps(set.getReps())
                                        .weight(set.getWeight())
                                        .order(set.getSetOrder())
                                        .loggedAt(set.getLoggedAt())
                                        .build())
                                .toList())
                        .build())
                .toList();

        return WorkoutExerciseSetResponse.builder()
                .workoutId(workout.getId())
                .workoutName(workout.getName())
                .date(workout.getDate())
                .workoutExercises(exercises)
                .build();
    }

    public List<ExerciseStats> getExerciseStats(int userId, int exerciseId) {
        var workoutExercises = workoutExerciseService.findByWorkoutUserIdAndExerciseId(userId, exerciseId);

        return workoutExercises.stream()
                .map(we -> ExerciseStats.builder()
                        .exerciseName(we.getExercise().getName())
                        .muscleGroup(we.getExercise().getMuscleGroup())
                        .date(we.getWorkout().getDate())
                        .sets(
                                we.getSets().stream()
                                        .map(s -> SetResponse.builder()
                                                .reps(s.getReps())
                                                .weight(s.getWeight())
                                                .order(s.getSetOrder())
                                                .loggedAt(s.getLoggedAt())
                                                .build())
                                        .toList())
                        .build())
                .sorted(Comparator.comparing(ExerciseStats::getDate).reversed())
                .toList();
    }

    public List<ExerciseStats> getProfileData(int userId) {
        var workoutExercises = workoutExerciseService.findByWorkoutUserId(userId);

        return workoutExercises.stream()
                .map(we -> ExerciseStats.builder()
                        .exerciseName(we.getExercise().getName())
                        .muscleGroup(we.getExercise().getMuscleGroup())
                        .date(we.getWorkout().getDate())
                        .sets(
                                we.getSets().stream()
                                        .map(s -> SetResponse.builder()
                                                .reps(s.getReps())
                                                .weight(s.getWeight())
                                                .order(s.getSetOrder())
                                                .loggedAt(s.getLoggedAt())
                                                .build())
                                        .toList())
                        .build())
                .sorted(Comparator.comparing(ExerciseStats::getDate).reversed())
                .toList();
    }
}
