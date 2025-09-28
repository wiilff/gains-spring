package com.wilff.gains_spring.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.PostResponse;
import com.wilff.gains_spring.model.Post;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.model.Workout;
import com.wilff.gains_spring.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserServiceImpl userService;
    private final WorkoutServiceImpl workoutService;

    public Post createPost(String authorEmail, String content, Integer workoutId) {
        User author = userService.findByEmail(authorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = workoutService.getById(workoutId);

        Post post = Post.builder()
                .author(author)
                .content(content)
                .workout(workout)
                .createdAt(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }

    public List<PostResponse> getFeed(String email) {
        User currentUser = userService.findByEmail(email).orElseThrow();
        List<Post> posts = postRepository.findPostsVisibleToUser(currentUser);

        return posts.stream().map(p -> PostResponse.builder()
                .id(p.getId())
                .authorName(p.getAuthor().getName())
                .authorEmail(p.getAuthor().getEmail())
                .content(p.getContent())
                .createdAt(p.getCreatedAt())
                .workoutName(p.getWorkout() != null ? p.getWorkout().getName() : null)
                .workoutId(p.getWorkout() != null ? p.getWorkout().getId() : null)
                .build()).toList();
    }
}
