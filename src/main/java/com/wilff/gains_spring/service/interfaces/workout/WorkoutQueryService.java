package com.wilff.gains_spring.service.interfaces.workout;

import java.util.List;

import com.wilff.gains_spring.dto.response.AllUserWorkoutsResponse;
import com.wilff.gains_spring.model.Workout;

public interface WorkoutQueryService {
    Workout getById(int id);
    List<AllUserWorkoutsResponse> getAllByUserId(int userId);
}
