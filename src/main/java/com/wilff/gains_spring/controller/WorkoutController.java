package com.wilff.gains_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.wilff.gains_spring.dto.request.CreateWorkoutRequest;
import com.wilff.gains_spring.dto.response.AllUserWorkoutsResponse;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.service.impl.UserServiceImpl;
import com.wilff.gains_spring.service.impl.WorkoutServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutServiceImpl workoutService;
    private final UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<AllUserWorkoutsResponse> workouts = workoutService.getAllByUserId(user.getId());
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody CreateWorkoutRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        
        Workout workout = Workout.builder()
                .name(request.getName())
                .date(request.getDate())
                .note(request.getNote())
                .isShared(request.isShared())
                .user(user)
                .build();

        Workout savedWorkout = workoutService.create(workout);

        System.out.println(savedWorkout.isShared());

        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);

    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable int workoutId,
            @Valid @RequestBody CreateWorkoutRequest request) {

        Workout existingWorkout = workoutService.getById(workoutId);

        existingWorkout.setName(request.getName());
        existingWorkout.setDate(request.getDate());
        existingWorkout.setNote(request.getNote());
        existingWorkout.setShared(request.isShared());

        workoutService.update(workoutId, existingWorkout);

        return new ResponseEntity<>(existingWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<String> deleteWorkout(@PathVariable int workoutId) {
        workoutService.delete(workoutId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
