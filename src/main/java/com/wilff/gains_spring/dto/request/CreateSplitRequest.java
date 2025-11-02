package com.wilff.gains_spring.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateSplitRequest {

    private String name;
    private String description;

    private List<WeeklyExercises> weeklyExercises;

}
