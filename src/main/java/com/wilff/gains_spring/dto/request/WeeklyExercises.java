package com.wilff.gains_spring.dto.request;

import com.wilff.gains_spring.dto.response.ExerciseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyExercises {
    private Map<String, List<ExerciseDTO>> exercises;
}
