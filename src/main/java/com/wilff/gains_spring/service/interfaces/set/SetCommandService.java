package com.wilff.gains_spring.service.interfaces.set;

import java.util.List;

import com.wilff.gains_spring.dto.request.PostSetRequest;
import com.wilff.gains_spring.model.LiftingSet;

public interface SetCommandService {
    LiftingSet update(int id, LiftingSet newSet);
    void delete(int id);
    List<LiftingSet> createAll(int workoutExerciseId, List<PostSetRequest> setList);
}
