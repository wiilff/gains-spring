package com.wilff.gains_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wilff.gains_spring.model.Post;
import com.wilff.gains_spring.model.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("""
        SELECT p FROM Post p
        WHERE p.author = :user
           OR p.author IN (
                SELECT uc.receiver FROM UserConnection uc
                WHERE uc.sender = :user AND uc.status = 'ACCEPTED'
                UNION
                SELECT uc.sender FROM UserConnection uc
                WHERE uc.receiver = :user AND uc.status = 'ACCEPTED'
           )
        ORDER BY p.createdAt DESC
    """)
    List<Post> findPostsVisibleToUser(@Param("user") User user);

}
