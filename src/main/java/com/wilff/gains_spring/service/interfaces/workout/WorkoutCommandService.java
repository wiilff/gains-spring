package com.wilff.gains_spring.service.interfaces.workout;

import com.wilff.gains_spring.model.Workout;

public interface WorkoutCommandService {
    Workout create(Workout workout);
    Workout update(int id, Workout newWorkout);
    void delete(int id);
}
