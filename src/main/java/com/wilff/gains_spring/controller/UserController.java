package com.wilff.gains_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wilff.gains_spring.dto.response.UserResponse;
import com.wilff.gains_spring.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        var returnedUser = UserResponse.builder()
                            .email(user.getUsername())
                            .name(user.getName())
                            .build();

        return new ResponseEntity<>(returnedUser, HttpStatus.OK);
    }
}