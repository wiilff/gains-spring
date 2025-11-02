package com.wilff.gains_spring.dto.response;

import java.time.LocalDateTime;

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
public class AllUserWorkoutsResponse {
    
    private int id;
    private int userId;
    private String name;
    private String note;
    private LocalDateTime date;
    private int exerciseCount;
    private int setCount;
    private boolean isShared;

}
