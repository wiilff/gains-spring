package com.wilff.gains_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilff.gains_spring.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer>{
    
    List<Workout> findByUserId(int userId);
}
