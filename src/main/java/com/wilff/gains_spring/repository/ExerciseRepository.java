package com.wilff.gains_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wilff.gains_spring.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    
    @Query("SELECT COUNT(DISTINCT we.exercise) " +
           "FROM WorkoutExercise we " +
           "WHERE we.workout.id = :workoutId")
    int countExercisesByWorkoutId(@Param("workoutId") int workoutId);
    
}
