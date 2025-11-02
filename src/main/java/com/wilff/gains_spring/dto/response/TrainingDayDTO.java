package com.wilff.gains_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDayDTO {

    private int id;
    private String dayOfWeek;
    private Set<ExerciseDTO> exercises;

}
