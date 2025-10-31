package com.wilff.gains_spring.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wilff.gains_spring.service.interfaces.ISetService;
import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.request.PostSetRequest;
import com.wilff.gains_spring.model.LiftingSet;
import com.wilff.gains_spring.model.WorkoutExercise;
import com.wilff.gains_spring.repository.SetRepository;
import com.wilff.gains_spring.repository.WorkoutExerciseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SetService implements ISetService {

    private final SetRepository setRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    @Override
    public List<LiftingSet> getByWorkoutExercise(WorkoutExercise workoutExercise) {
        return setRepository.findByWorkoutExerciseOrderBySetOrder(workoutExercise);
    }

    @Override
    public int countSetsByWorkoutId(int workoutId) {
        return setRepository.countSetsByWorkoutId(workoutId);
    }

    @Override
    public LiftingSet getById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    @Transactional
    public List<LiftingSet> createAll(int workoutExerciseId, List<PostSetRequest> setList) {
        var workoutExercise = workoutExerciseRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new RuntimeException("WorkoutExercise not found: " + workoutExerciseId));

        List<LiftingSet> resultSets = new ArrayList<>();

        for (PostSetRequest req : setList) {
            LiftingSet set;
            if (req.getId() != null) {
                // Existing set, fetch and update
                set = setRepository.findById(req.getId())
                        .orElseThrow(() -> new RuntimeException("Set not found: " + req.getId()));
                set.setReps(req.getReps());
                set.setWeight(req.getWeight());
                set.setSetOrder(req.getOrder());
            } else {
                // New set
                set = LiftingSet.builder()
                        .reps(req.getReps())
                        .weight(req.getWeight())
                        .setOrder(req.getOrder())
                        .workoutExercise(workoutExercise)
                        .build();
            }
            resultSets.add(set);
        }

        return setRepository.saveAll(resultSets);
    }

    @Override
    public int countByUserId(int userId) {
        return setRepository.countByUserId(userId);
    }

    @Override
    public LiftingSet update(int id, LiftingSet newSet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        setRepository.deleteById(id);
    }

}
