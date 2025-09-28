package com.wilff.gains_spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.UserConnection;
import com.wilff.gains_spring.model.enums.RequestStatus;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {
    List<UserConnection> findByReceiverAndStatus(User receiver, RequestStatus status);
    List<UserConnection> findBySenderAndStatus(User requester, RequestStatus status);
    Optional<UserConnection> findBySenderAndReceiver(User requester, User receiver);
    List<UserConnection> findBySenderOrReceiver(User requester, User receiver);
}
