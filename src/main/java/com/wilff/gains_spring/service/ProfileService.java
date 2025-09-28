package com.wilff.gains_spring.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.ExerciseStats;
import com.wilff.gains_spring.dto.response.ProfileData;
import com.wilff.gains_spring.dto.response.SetResponse;
import com.wilff.gains_spring.repository.WorkoutRepository;
import com.wilff.gains_spring.service.impl.WorkoutExerciseServiceImpl;

import ch.qos.logback.core.util.Duration;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    
    private final WorkoutExerciseServiceImpl workoutExerciseService; 

}

