package com.wilff.gains_spring.controller;

import java.util.List;

import com.wilff.gains_spring.service.interfaces.ISetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wilff.gains_spring.dto.request.PostSetRequest;
import com.wilff.gains_spring.model.LiftingSet;
import com.wilff.gains_spring.service.impl.SetService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/workouts/exercises/sets")
public class SetController {

    private final ISetService setService;
 
    @PutMapping("/{workoutExerciseId}")
    public ResponseEntity<List<LiftingSet>> addSets(@PathVariable int workoutExerciseId, @RequestBody List<PostSetRequest> setList) {
        List<LiftingSet> list = setService.createAll(workoutExerciseId, setList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{setId}")
    public ResponseEntity<String> getWorkoutDetails(@PathVariable int setId) {
        setService.delete(setId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
