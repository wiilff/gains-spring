package com.wilff.gains_spring.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFriendStatusResponse {
    
    private int id;
    private String name;
    private String email;
    private String status;

}
