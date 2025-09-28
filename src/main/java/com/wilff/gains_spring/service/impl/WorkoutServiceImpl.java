package com.wilff.gains_spring.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.AllUserWorkoutsResponse;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.repository.WorkoutRepository;
import com.wilff.gains_spring.service.interfaces.workout.WorkoutCommandService;
import com.wilff.gains_spring.service.interfaces.workout.WorkoutQueryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutCommandService, WorkoutQueryService {
    
    private final WorkoutRepository workoutRepository;
    private final SetService setService;
    private final ExerciseService exerciseService;

    @Override
    public List<AllUserWorkoutsResponse> getAllByUserId(int userId) {
        List<Workout> workouts = workoutRepository.findByUserId(userId);

        return workouts.stream()
                .map(w -> AllUserWorkoutsResponse.builder()
                    .id(w.getId())
                    .userId(userId)
                    .name(w.getName())
                    .date(w.getDate())
                    .isShared(w.isShared())
                    .exerciseCount(exerciseService.countExercisesByWorkoutId(w.getId()))
                    .setCount(setService.countSetsByWorkoutId(w.getId()))

                    .build()
                    
                )
                .sorted(Comparator.comparing(AllUserWorkoutsResponse::getDate).reversed())
                .toList();
    }

    

    @Override
    public Workout getById(int id) {
        return workoutRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Workout id: " + id + " not found"));
    }
    
    @Override
    public Workout create(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout update(int id, Workout newWorkout) {
        return workoutRepository.save(newWorkout);
    }

    @Override
    public void delete(int id) {
        workoutRepository.deleteById(id);
    }

}
