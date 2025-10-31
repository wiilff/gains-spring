package com.wilff.gains_spring.service.impl;

import java.util.List;

import com.wilff.gains_spring.service.interfaces.IWorkoutExerciseService;
import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.UserExerciseOverallStats;
import com.wilff.gains_spring.model.Exercise;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.model.WorkoutExercise;
import com.wilff.gains_spring.repository.ExerciseRepository;
import com.wilff.gains_spring.repository.WorkoutExerciseRepository;
import com.wilff.gains_spring.repository.WorkoutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseServiceImpl implements IWorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<WorkoutExercise> getAllByWorkout(Workout workout) {
        return workoutExerciseRepository.findByWorkout(workout);
    }

    @Override
    public WorkoutExercise create(int workoutId, int exerciseId) {
        Exercise exercise = exerciseRepository.getReferenceById(exerciseId);
        Workout workout = workoutRepository.getReferenceById(workoutId);

        WorkoutExercise workoutExercise = WorkoutExercise.builder()
            .exercise(exercise)
            .workout(workout)
            .build();
        return workoutExerciseRepository.save(workoutExercise);
    }

    @Override
    public List<WorkoutExercise> findByWorkoutUserIdAndExerciseId(int userId, int exerciseId) {
        return workoutExerciseRepository.findByWorkoutUserIdAndExerciseId(userId, exerciseId);
    }

    @Override
    public List<WorkoutExercise> findByWorkoutUserId(int userId) {
        return workoutExerciseRepository.findByWorkoutUserId(userId);
    }

    @Override
    public void delete(int workoutExerciseId) {
        workoutExerciseRepository.deleteById(workoutExerciseId);
    }

    @Override
    public List<UserExerciseOverallStats> getWorkoutExerciseStats(int userId) {
        return workoutExerciseRepository.getWorkoutExerciseStats(userId);
    }

    public Object getById(int workoutExerciseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public int countTotalWorkoutExercises(int userId) {
        return workoutExerciseRepository.countDistinctByWorkout_UserId(userId);
    }

    
}
