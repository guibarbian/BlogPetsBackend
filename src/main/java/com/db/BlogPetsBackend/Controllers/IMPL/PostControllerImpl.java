package com.db.BlogPetsBackend.Controllers.IMPL;

import com.db.BlogPetsBackend.Controllers.PostController;
import com.db.BlogPetsBackend.DTO.Request.RequestPostDTO;
import com.db.BlogPetsBackend.DTO.Response.ResponsePostDTO;
import com.db.BlogPetsBackend.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDTO> getPostById(@PathVariable Long postId) {
        return ResponseEntity.status(200).body(postService.getPostById(postId));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ResponsePostDTO>> getAllPosts() {
        return ResponseEntity.status(200).body(postService.getAllPosts());
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponsePostDTO> createPost(@RequestBody RequestPostDTO dto) {
        return ResponseEntity.status(201).body(postService.createPost(dto));
    }

    @Override
    @PutMapping("/{postId}")
    public ResponseEntity<ResponsePostDTO> updatePost(
            @PathVariable Long postId, @RequestBody RequestPostDTO dto) {
        return ResponseEntity.status(200).body(postService.updatePost(postId, dto));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(200).body("Post deleted");
    }
}
