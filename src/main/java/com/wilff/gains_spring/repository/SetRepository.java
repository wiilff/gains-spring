package com.wilff.gains_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wilff.gains_spring.model.LiftingSet;
import com.wilff.gains_spring.model.WorkoutExercise;

public interface SetRepository extends JpaRepository<LiftingSet, Integer> {
    
    @Query("SELECT COUNT(s) " +
           "FROM WorkoutExercise we JOIN we.sets s " +
           "WHERE we.workout.id = :workoutId")
    int countSetsByWorkoutId(@Param("workoutId") int workoutId);

    List<LiftingSet> findByWorkoutExerciseOrderBySetOrder(WorkoutExercise workoutExercise);
}
