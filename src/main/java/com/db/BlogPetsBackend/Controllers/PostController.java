package com.db.BlogPetsBackend.Controllers;

import com.db.BlogPetsBackend.DTO.Request.RequestPostDTO;
import com.db.BlogPetsBackend.DTO.Response.ResponsePostDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PostController {

    @GetMapping("/{postId}")
    ResponseEntity<ResponsePostDTO> getPostById(@PathVariable Long postId);

    @GetMapping
    ResponseEntity<List<ResponsePostDTO>> getAllPosts();

    @PostMapping
    ResponseEntity<ResponsePostDTO> createPost(@RequestBody @Valid RequestPostDTO dto);

    @PutMapping("/{postId}")
    ResponseEntity<ResponsePostDTO> updatePost(@PathVariable Long postId, @RequestBody @Valid RequestPostDTO dto);

    @DeleteMapping("/{postId}")
    ResponseEntity<String> deletePost(@PathVariable Long postId);
}
