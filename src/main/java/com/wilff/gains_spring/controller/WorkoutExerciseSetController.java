package com.wilff.gains_spring.controller;

import java.util.List;

import com.wilff.gains_spring.service.interfaces.IUserService;
import com.wilff.gains_spring.service.interfaces.IWorkoutExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private final IUserService userService;
    private final IWorkoutExerciseService workoutExerciseService;

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

        var newWorkoutExercise = workoutExerciseService.create(request.getWorkout_id(), request.getExercise_id(), request.getOrder());

        return new ResponseEntity<>(newWorkoutExercise, HttpStatus.OK);
    }

    @PostMapping("/{workoutId}")
    public ResponseEntity<?> setBulkExercisesToWorkout(@PathVariable int workoutId,
                                                       @RequestBody List<Integer> exerciseIds) {
        for(int i = 0; i< exerciseIds.size(); i++ ) {
            workoutExerciseService.create(workoutId, exerciseIds.get(i), i + 1);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{workoutExerciseId}")
    public ResponseEntity<?> deleteExerciseFromWorkout(@PathVariable int workoutExerciseId) {;
        workoutExerciseService.delete(workoutExerciseId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
