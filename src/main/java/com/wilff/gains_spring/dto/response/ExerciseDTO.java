package com.wilff.gains_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {

    private int id;
    private String name;
    private String description;
    private String muscleGroup;

}
