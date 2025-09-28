package com.wilff.gains_spring.service.interfaces.set;

import java.util.List;

import com.wilff.gains_spring.model.LiftingSet;
import com.wilff.gains_spring.model.WorkoutExercise;

public interface SetQueryService {
    List<LiftingSet> getByWorkoutExercise(WorkoutExercise workoutExercise);
    int countSetsByWorkoutId(int workoutId);
    LiftingSet getById(int id);
}
