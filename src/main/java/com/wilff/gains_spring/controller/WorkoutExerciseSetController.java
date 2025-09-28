package com.wilff.gains_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wilff.gains_spring.dto.request.PostExerciseToWorkout;
import com.wilff.gains_spring.dto.response.ExerciseStats;
import com.wilff.gains_spring.dto.response.UserExerciseOverallStats;
import com.wilff.gains_spring.dto.response.WorkoutExerciseSetResponse;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.service.WorkoutDetailService;
import com.wilff.gains_spring.service.impl.UserServiceImpl;
import com.wilff.gains_spring.service.impl.WorkoutExerciseServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/workouts/exercises")
public class WorkoutExerciseSetController {

    private final WorkoutDetailService workoutDetailService; 
    private final UserServiceImpl userService;
    private final WorkoutExerciseServiceImpl workoutExerciseService;

    @GetMapping("/sets/{workoutId}")
    public ResponseEntity<WorkoutExerciseSetResponse> getWorkoutDetails(@PathVariable int workoutId) {
        var workoutDetails = workoutDetailService.getWorkoutWithDetails(workoutId);
        return new ResponseEntity<>(workoutDetails, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<UserExerciseOverallStats>> getExerciseStats(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<UserExerciseOverallStats> list = workoutExerciseService.getWorkoutExerciseStats(user.getId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    } 

    @GetMapping("/stats/profile")
    public ResponseEntity<List<?>> getProfileStats(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<ExerciseStats> exerciseStats = workoutDetailService.getProfileData(user.getId());
        return new ResponseEntity<>(exerciseStats, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> setExerciseToWorkout(@RequestBody PostExerciseToWorkout request) {;

        var newWorkoutExercise = workoutExerciseService.create(request.getWorkout_id(), request.getExercise_id());

        return new ResponseEntity<>(newWorkoutExercise, HttpStatus.OK);
    }

    @DeleteMapping("/{workoutExerciseId}")
    public ResponseEntity<?> deleteExerciseFromWorkout(@PathVariable int workoutExerciseId) {;
        workoutExerciseService.delete(workoutExerciseId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
