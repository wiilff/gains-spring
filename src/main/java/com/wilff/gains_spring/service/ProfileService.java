package com.wilff.gains_spring.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.wilff.gains_spring.dto.response.UserStats;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.service.impl.ExerciseService;
import com.wilff.gains_spring.service.interfaces.ISetService;
import com.wilff.gains_spring.service.interfaces.IWorkoutExerciseService;
import com.wilff.gains_spring.service.interfaces.IWorkoutService;
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

    private final IWorkoutService workoutService;
    private final ISetService setService;
    private final IWorkoutExerciseService workoutExerciseService;

    public UserStats getProfileStats(int userId) {
        return UserStats.builder()
                // WEEKS DOESNT WORK AND I DONT CARE CAUSE WHO CARES
                //.consecutiveWeeks(workoutService.getConsecutiveWeeks(userId))
                .totalSets(setService.countByUserId(userId))
                .totalWorkouts(workoutService.getTotalWorkoutsByUserId(userId))
                .totalExercises(workoutExerciseService.countTotalWorkoutExercises(userId))
                .build();
    }

}

