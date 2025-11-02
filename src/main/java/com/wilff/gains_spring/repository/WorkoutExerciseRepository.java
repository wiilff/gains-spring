package com.wilff.gains_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wilff.gains_spring.dto.response.UserExerciseOverallStats;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.model.WorkoutExercise;
import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {

    List<WorkoutExercise> findByWorkout(Workout workout);

    @Query("""
                SELECT new com.wilff.gains_spring.dto.response.UserExerciseOverallStats(
                    e.id,
                    e.name,
                    COUNT(s.id),
                    COUNT(DISTINCT w.id)
                )
                FROM Workout w
                JOIN w.workoutExercises we
                JOIN we.exercise e
                LEFT JOIN we.sets s
                WHERE w.user.id = :userId
                GROUP BY e.id, e.name
            """)
    List<UserExerciseOverallStats> getWorkoutExerciseStats(@Param("userId") int userId);

    List<WorkoutExercise> findByWorkoutUserIdAndExerciseId(int userId, int exerciseId);
    List<WorkoutExercise> findByWorkoutUserId(int userId);

    List<WorkoutExercise> findByWorkoutIdOrderByExerciseOrder(int workoutId);


    @Query("SELECT COUNT(DISTINCT we.exercise.id) FROM WorkoutExercise we WHERE we.workout.user.id = :userId")
    int countDistinctByWorkout_UserId(@Param("userId") int userId);
}
