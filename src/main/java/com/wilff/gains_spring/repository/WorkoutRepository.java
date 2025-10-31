package com.wilff.gains_spring.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilff.gains_spring.model.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutRepository extends JpaRepository<Workout, Integer>{
    
    List<Workout> findByUserId(int userId);

    @Query("SELECT COUNT(w) FROM Workout w WHERE w.user.id = :userId")
    int countByUserId(@Param("userId") int userId);

    @Query("SELECT w.date FROM Workout w WHERE w.user.id = :userId")
    List<LocalDateTime> findAllDatesByUserId(int userId);
}
