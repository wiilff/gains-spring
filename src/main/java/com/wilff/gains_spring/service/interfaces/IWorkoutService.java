package com.wilff.gains_spring.service.interfaces;

import com.wilff.gains_spring.dto.response.AllUserWorkoutsResponse;
import com.wilff.gains_spring.model.Workout;

import java.util.List;

public interface IWorkoutService {
    Workout create(Workout workout);
    Workout update(int id, Workout newWorkout);
    void delete(int id);

    int getConsecutiveWeeks(int userId);

    int getTotalWorkoutsByUserId(int userId);

    Workout getById(int id);
    List<AllUserWorkoutsResponse> getAllByUserId(int userId);
}
