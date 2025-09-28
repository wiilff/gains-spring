package com.wilff.gains_spring.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilff.gains_spring.model.Exercise;
import com.wilff.gains_spring.model.LiftingSet;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.model.WorkoutExercise;
import com.wilff.gains_spring.model.enums.MuscleGroup;
import com.wilff.gains_spring.model.enums.UserRole;
import com.wilff.gains_spring.repository.ExerciseRepository;
import com.wilff.gains_spring.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TestController {

        private UserRepository userRepository;
        private ExerciseRepository exerciseRepository;
        
        @GetMapping("/test")
        public String init() {

                userRepository.deleteAll();
                exerciseRepository.deleteAll();

                // Create exercise first
                Exercise exercise = Exercise.builder()
                                .name("Bench")
                                .muscleGroup(MuscleGroup.CHEST)
                                .build();
                exerciseRepository.save(exercise); // saved because not cascaded

                // Create user
                User user = User.builder()
                                .name("test")
                                .email("m@m.com")
                                .password("test")
                                .userRole(UserRole.ADMIN)
                                .build();

                // Create workout
                Workout workout = Workout.builder()
                                .name("My workout")
                                .build();
                workout.setUser(user);
                user.getWorkouts().add(workout);

                // Create WorkoutExercise
                WorkoutExercise we = WorkoutExercise.builder()
                                .workout(workout)
                                .exercise(exercise)
                                .build();

                // Create sets
                LiftingSet set1 = LiftingSet.builder().reps(2).weight(20).setOrder(1).workoutExercise(we).build();
                LiftingSet set2 = LiftingSet.builder().reps(3).weight(30).setOrder(2).workoutExercise(we).build();
                LiftingSet set3 = LiftingSet.builder().reps(4).weight(40).setOrder(3).workoutExercise(we).build();

                // Link sets to WorkoutExercise
                we.setSets(Set.of(set1, set2, set3));

                // Link WorkoutExercise to workout and exercise
                workout.getWorkoutExercises().add(we);
                exercise.getWorkoutExercises().add(we);

                // Save only the root (user) → cascades to workout → cascades to workoutExercise
                // → cascades to sets
                userRepository.save(user);

                return "OK";
        }

}
