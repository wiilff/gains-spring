package com.wilff.gains_spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wilff.gains_spring.service.interfaces.IExerciseService;
import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.ExerciseResponse;
import com.wilff.gains_spring.model.Exercise;
import com.wilff.gains_spring.repository.ExerciseRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseService implements IExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public Exercise getById(int id) {
        return exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Workout id: " + id + " not found"));
    }

    @Override
    public List<ExerciseResponse> getAll() {
        List<Exercise> exercises = exerciseRepository.findAll();
        List<ExerciseResponse> exercisesResponses = new ArrayList<>();

        for(Exercise exercise : exercises) {
            ExerciseResponse exerciseResponse = ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .muscleGroup(exercise.getMuscleGroup())
                .build();

            exercisesResponses.add(exerciseResponse);
        }

        return exercisesResponses;
    }


    @Override
    public int countExercisesByWorkoutId(int workoutId) {
        return exerciseRepository.countExercisesByWorkoutId(workoutId);
    }

    @Override
    public Exercise create(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise update(int id, Exercise newExercise) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
