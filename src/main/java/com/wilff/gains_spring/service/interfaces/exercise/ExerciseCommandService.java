package com.wilff.gains_spring.service.interfaces.exercise;

import com.wilff.gains_spring.model.Exercise;

public interface ExerciseCommandService {
    Exercise create(Exercise exercise);
    Exercise update(int id, Exercise newExercise);
    void delete(int id);
}
