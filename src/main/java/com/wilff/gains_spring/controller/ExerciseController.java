package com.wilff.gains_spring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilff.gains_spring.dto.request.PostExerciseRequest;
import com.wilff.gains_spring.dto.response.ExerciseResponse;
import com.wilff.gains_spring.dto.response.ExerciseStats;
import com.wilff.gains_spring.model.Exercise;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.enums.MuscleGroup;
import com.wilff.gains_spring.service.WorkoutDetailService;
import com.wilff.gains_spring.service.impl.ExerciseService;
import com.wilff.gains_spring.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final UserServiceImpl userService;
    private final WorkoutDetailService workoutDetailService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<ExerciseResponse> exercises = exerciseService.getAll();
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<List<?>> getExerciseStats(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int exerciseId) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<ExerciseStats> exerciseStats = workoutDetailService.getExerciseStats(user.getId(), exerciseId);
        return new ResponseEntity<>(exerciseStats, HttpStatus.OK);
    }

    @GetMapping("/muscle")
    public ResponseEntity<List<MuscleGroup>> getMuscleGroups() {
        return new ResponseEntity<>(Arrays.asList(MuscleGroup.values()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody PostExerciseRequest request) {
        Exercise exercise = Exercise.builder()
            .name(request.getName())
            .muscleGroup(request.getMuscleGroup())
            .build();
        var createdExercise = exerciseService.create(exercise);

        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    
}