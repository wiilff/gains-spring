package com.wilff.gains_spring.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String name;
    private String email;
    
}