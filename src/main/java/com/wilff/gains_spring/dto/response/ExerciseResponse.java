package com.wilff.gains_spring.dto.response;


import com.wilff.gains_spring.model.enums.MuscleGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExerciseResponse {
    
    private int id;
    private String name;
    private String description;
    private MuscleGroup muscleGroup;
    
}
