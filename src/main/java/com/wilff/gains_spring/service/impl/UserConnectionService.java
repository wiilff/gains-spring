package com.wilff.gains_spring.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.UserFriendStatusResponse;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.UserConnection;
import com.wilff.gains_spring.model.enums.RequestStatus;
import com.wilff.gains_spring.repository.UserConnectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserConnectionService {

    private final UserConnectionRepository userConnectionRepository;
    private final UserServiceImpl userService;

    public UserConnection sendFriendRequest(String requesterEmail, String receiverEmail) {
        User sender = userService.findByEmail(requesterEmail)
                .orElseThrow(() -> new RuntimeException("Requester not found"));
        User receiver = userService.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Optional<UserConnection> existingConnection = userConnectionRepository
                .findBySenderAndReceiver(sender, receiver)
                .or(() -> userConnectionRepository.findBySenderAndReceiver(receiver, sender));

        if (existingConnection.isPresent()) {
            UserConnection connection = existingConnection.get();
            if (connection.getStatus() == RequestStatus.REJECTED || connection.getStatus() == null) {
                connection.setSender(sender); 
                connection.setReceiver(receiver); 
                connection.setStatus(RequestStatus.PENDING);
                return userConnectionRepository.save(connection);
            }
            return connection;
        }

        UserConnection connection = new UserConnection();
        connection.setSender(sender);
        connection.setReceiver(receiver);
        connection.setStatus(RequestStatus.PENDING);
        return userConnectionRepository.save(connection);
    }

    public UserConnection respondToRequest(int requestId, boolean accept) {
        UserConnection connection = userConnectionRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        connection.setStatus(accept ? RequestStatus.ACCEPTED : RequestStatus.REJECTED);
        return userConnectionRepository.save(connection);
    }

    public List<UserConnection> getPendingRequests(String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userConnectionRepository.findByReceiverAndStatus(user, RequestStatus.PENDING);
    }

    public List<UserFriendStatusResponse> getAllUsersWithStatus(User currentUser) {
        List<User> allUsers = userService.getAll();

        List<UserConnection> friendships = userConnectionRepository.findBySenderOrReceiver(currentUser, currentUser);

        return allUsers.stream()
                .filter(u -> u.getId() != currentUser.getId())
                .map(u -> {
                    String status = "NONE";
                    Integer connectionId = null;

                    // find if this user has a friendship with currentUser
                    Optional<UserConnection> f = friendships.stream()
                            .filter(fr -> (fr.getSender().equals(currentUser) && fr.getReceiver().equals(u)) ||
                                    (fr.getReceiver().equals(currentUser) && fr.getSender().equals(u)))
                            .findFirst();

                    if (f.isPresent()) {
                        UserConnection fr = f.get();
                        connectionId = fr.getId();
                        switch (fr.getStatus()) {
                            case ACCEPTED:
                                status = "FRIEND";
                                break;
                            case PENDING:
                                if (fr.getSender().equals(currentUser)) {
                                    status = "PENDING_SENT";
                                } else {
                                    status = "PENDING_RECEIVED";
                                }
                                break;
                            default:
                                status = "NONE";
                                break;
                        }
                    }

                    return UserFriendStatusResponse.builder()
                            .id(connectionId != null ? connectionId : 0)
                            .name(u.getName())
                            .email(u.getEmail())
                            .status(status)
                            .build();
                })
                .toList();
    }
}
