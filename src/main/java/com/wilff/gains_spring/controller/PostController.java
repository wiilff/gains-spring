package com.wilff.gains_spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wilff.gains_spring.dto.request.CreatePostRequest;
import com.wilff.gains_spring.dto.response.PostResponse;
import com.wilff.gains_spring.model.Post;
import com.wilff.gains_spring.service.impl.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreatePostRequest post) {
        Post createdPost = postService.createPost(userDetails.getUsername(), post.getContent(), post.getWorkoutId());
        return ResponseEntity.ok(post);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed(@AuthenticationPrincipal UserDetails userDetails) {
        List<PostResponse> feed = postService.getFeed(userDetails.getUsername());
        return ResponseEntity.ok(feed);
    }
}
