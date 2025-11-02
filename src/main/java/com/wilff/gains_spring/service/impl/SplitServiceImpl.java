package com.wilff.gains_spring.service.impl;

import com.wilff.gains_spring.dto.request.CreateSplitRequest;
import com.wilff.gains_spring.dto.request.WeeklyExercises;
import com.wilff.gains_spring.dto.response.ExerciseDTO;
import com.wilff.gains_spring.dto.response.SplitDTO;
import com.wilff.gains_spring.dto.response.TrainingDayDTO;
import com.wilff.gains_spring.model.Exercise;
import com.wilff.gains_spring.model.Split;
import com.wilff.gains_spring.model.TrainingDay;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.enums.MuscleGroup;
import com.wilff.gains_spring.repository.ExerciseRepository;
import com.wilff.gains_spring.repository.SplitRepository;
import com.wilff.gains_spring.repository.TrainingDayRepository;
import com.wilff.gains_spring.service.interfaces.ISplitService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SplitServiceImpl implements ISplitService {

    private final SplitRepository splitRepository;
    private final TrainingDayRepository trainingDayRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<SplitDTO> getSplitsByUserId(int userId) {

        return splitRepository.findAllByUserId(userId).stream().map(split -> SplitDTO.builder()
                .id(split.getId())
                .name(split.getName())
                .description(split.getDescription())
                .trainingDays(
                        split.getTrainingDays().stream()
                                .map(td -> TrainingDayDTO.builder()
                                        .id(td.getId())
                                        .dayOfWeek(td.getDayOfWeek().toString())
                                        .exercises(td.getExercises().stream()
                                                .map(e -> ExerciseDTO.builder()
                                                        .id(e.getId())
                                                        .name(e.getName())
                                                        .description(e.getDescription())
                                                        .muscleGroup(e.getMuscleGroup().name())
                                                        .build())
                                                .collect(Collectors.toSet()))
                                        .build())
                                .collect(Collectors.toList()))
                .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SplitDTO createSplit(CreateSplitRequest dto, User user) {

        Split split = Split.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .user(user)
                .build();

        Map<String, List<ExerciseDTO>> exercisesByDay = new HashMap<>();
        if (dto.getWeeklyExercises() != null) {
            for (WeeklyExercises week : dto.getWeeklyExercises()) {
                if (week.getExercises() != null) {
                    week.getExercises().forEach((day, exercises) -> {
                        exercisesByDay.merge(day, exercises, (oldList, newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        });
                    });
                }
            }
        }

        Set<TrainingDay> trainingDays = new HashSet<>();
        for (Map.Entry<String, List<ExerciseDTO>> entry : exercisesByDay.entrySet()) {
            String dayName = entry.getKey();
            List<ExerciseDTO> exerciseDTOs = entry.getValue();

            if (exerciseDTOs == null || exerciseDTOs.isEmpty()) {
                continue;
            }

            TrainingDay trainingDay = new TrainingDay();
            trainingDay.setDayOfWeek(DayOfWeek.valueOf(dayName.toUpperCase()));
            trainingDay.setSplit(split);


            Set<Exercise> exerciseEntities = exerciseDTOs.stream()
                    .map(dtoEx -> exerciseRepository.findById(dtoEx.getId())
                            .orElseGet(() -> Exercise.builder()
                                    .name(dtoEx.getName())
                                    .description(dtoEx.getDescription())
                                    .muscleGroup(MuscleGroup.valueOf(dtoEx.getMuscleGroup()))
                                    .build()))
                    .collect(Collectors.toSet());

            if(!exerciseEntities.isEmpty()) {
                trainingDay.setExercises(exerciseEntities);
                trainingDays.add(trainingDay);
            }

        }

        split.setTrainingDays(trainingDays);

        Split savedSplit = splitRepository.save(split);

        return SplitDTO.builder()
                .id(savedSplit.getId())
                .name(savedSplit.getName())
                .description(savedSplit.getDescription())
                .trainingDays(savedSplit.getTrainingDays().stream()
                        .map(td -> TrainingDayDTO.builder()
                                .id(td.getId())
                                .dayOfWeek(td.getDayOfWeek().name())
                                .exercises(td.getExercises().stream()
                                        .map(e -> ExerciseDTO.builder()
                                                .id(e.getId())
                                                .name(e.getName())
                                                .description(e.getDescription())
                                                .muscleGroup(e.getMuscleGroup().name())
                                                .build())
                                        .collect(Collectors.toSet()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    @Override
    public SplitDTO updateSplit(int splitId, CreateSplitRequest dto) {
        // Find existing split
        Split split = splitRepository.findById(splitId).orElseThrow();

        // Update basic fields
        split.setName(dto.getName());
        split.setDescription(dto.getDescription());

        // Prepare new training days
        Map<String, List<ExerciseDTO>> exercisesByDay = new HashMap<>();
        if (dto.getWeeklyExercises() != null) {
            for (WeeklyExercises week : dto.getWeeklyExercises()) {
                if (week.getExercises() != null) {
                    week.getExercises().forEach((day, exercises) -> {
                        exercisesByDay.merge(day, exercises, (oldList, newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        });
                    });
                }
            }
        }

        // Clear existing training days
        split.getTrainingDays().clear();

        // Create updated training days
        for (Map.Entry<String, List<ExerciseDTO>> entry : exercisesByDay.entrySet()) {
            String dayName = entry.getKey();
            List<ExerciseDTO> exerciseDTOs = entry.getValue();

            if (exerciseDTOs == null || exerciseDTOs.isEmpty()) {
                continue;
            }

            TrainingDay trainingDay = new TrainingDay();
            trainingDay.setDayOfWeek(DayOfWeek.valueOf(dayName.toUpperCase()));
            trainingDay.setSplit(split);

            Set<Exercise> exerciseEntities = exerciseDTOs.stream()
                    .map(dtoEx -> exerciseRepository.findById(dtoEx.getId())
                            .orElseGet(() -> Exercise.builder()
                                    .name(dtoEx.getName())
                                    .description(dtoEx.getDescription())
                                    .muscleGroup(MuscleGroup.valueOf(dtoEx.getMuscleGroup()))
                                    .build()))
                    .collect(Collectors.toSet());

            if (!exerciseEntities.isEmpty()) {
                trainingDay.setExercises(exerciseEntities);
                split.getTrainingDays().add(trainingDay);
            }
        }

        // Save updated split
        Split updatedSplit = splitRepository.save(split);

        // Map back to DTO
        return SplitDTO.builder()
                .id(updatedSplit.getId())
                .name(updatedSplit.getName())
                .description(updatedSplit.getDescription())
                .trainingDays(updatedSplit.getTrainingDays().stream()
                        .map(td -> TrainingDayDTO.builder()
                                .id(td.getId())
                                .dayOfWeek(td.getDayOfWeek().name())
                                .exercises(td.getExercises().stream()
                                        .map(e -> ExerciseDTO.builder()
                                                .id(e.getId())
                                                .name(e.getName())
                                                .description(e.getDescription())
                                                .muscleGroup(e.getMuscleGroup().name())
                                                .build())
                                        .collect(Collectors.toSet()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }



}
