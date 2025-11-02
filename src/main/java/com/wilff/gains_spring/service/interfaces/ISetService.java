package com.wilff.gains_spring.service.interfaces;

import com.wilff.gains_spring.dto.request.PostSetRequest;
import com.wilff.gains_spring.model.LiftingSet;
import com.wilff.gains_spring.model.WorkoutExercise;

import java.util.List;

public interface ISetService {
    List<LiftingSet> getByWorkoutExercise(WorkoutExercise workoutExercise);
    int countSetsByWorkoutId(int workoutId);
    LiftingSet getById(int id);
    LiftingSet update(int id, LiftingSet newSet);
    void delete(int id);
    List<LiftingSet> createAll(int workoutExerciseId, List<PostSetRequest> setList);
    int countByUserId(int userId);
}
