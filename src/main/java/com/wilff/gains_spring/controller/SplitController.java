package com.wilff.gains_spring.controller;

import com.wilff.gains_spring.dto.request.CreateSplitRequest;
import com.wilff.gains_spring.dto.request.WeeklyExercises;
import com.wilff.gains_spring.dto.response.SplitDTO;
import com.wilff.gains_spring.model.Split;
import com.wilff.gains_spring.service.interfaces.ISplitService;
import com.wilff.gains_spring.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/splits")
public class SplitController {

    private final ISplitService splitService;
    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<List<SplitDTO>> findAllByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        return new ResponseEntity<>(splitService.getSplitsByUserId(user.getId()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SplitDTO> createSplit(@RequestBody CreateSplitRequest weeklyExercises,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        var createdSplit = splitService.createSplit(weeklyExercises, user);
        return new ResponseEntity<>(createdSplit, HttpStatus.OK);
    }

    @PutMapping("/{splitId}")
    public ResponseEntity<SplitDTO> editSplit(@PathVariable int splitId, @RequestBody CreateSplitRequest weeklyExercises) {

        var createdSplit = splitService.updateSplit(splitId, weeklyExercises);
        return new ResponseEntity<>(createdSplit, HttpStatus.OK);
    }

    @DeleteMapping("{splitId}")
    public ResponseEntity<?> deleteSplit(@PathVariable int splitId) {
        splitService.deleteById(splitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
