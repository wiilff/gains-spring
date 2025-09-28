package com.wilff.gains_spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wilff.gains_spring.dto.response.UserFriendStatusResponse;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.UserConnection;
import com.wilff.gains_spring.service.impl.UserConnectionService;
import com.wilff.gains_spring.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class ConnectionController {

    private final UserConnectionService connectionService;
    private final UserServiceImpl userService;

    @PostMapping("/request/{receiverEmail}")
    public ResponseEntity<UserConnection> sendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String receiverEmail) {
        UserConnection connection = connectionService.sendFriendRequest(userDetails.getUsername(), receiverEmail);
        return ResponseEntity.ok(connection);
    }

    @PostMapping("/respond/{requestId}")
    public ResponseEntity<UserConnection> respond(
            @PathVariable int requestId,
            @RequestParam boolean accept) {
        UserConnection connection = connectionService.respondToRequest(requestId, accept);
        return ResponseEntity.ok(connection);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserFriendStatusResponse>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(connectionService.getAllUsersWithStatus(currentUser));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<UserConnection>> pending(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<UserConnection> requests = connectionService.getPendingRequests(userDetails.getUsername());
        return ResponseEntity.ok(requests);
    }
}
