package com.wilff.gains_spring.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStats {

    private int totalWorkouts;
    private int totalSets;
    private int totalExercises;
    private int consecutiveWeeks;

}
