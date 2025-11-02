package com.wilff.gains_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SplitDTO {

    private int id;
    private String name;
    private String description;
    private List<TrainingDayDTO> trainingDays;

}
