package com.db.BlogPetsBackend.Services;

import com.db.BlogPetsBackend.DTO.Request.RequestPostDTO;
import com.db.BlogPetsBackend.DTO.Response.ResponsePostDTO;

import java.util.List;

public interface PostService {

    ResponsePostDTO getPostById(Long postIds);

    List<ResponsePostDTO> getAllPosts();

    ResponsePostDTO createPost(RequestPostDTO DTO);

    ResponsePostDTO updatePost(Long postId, RequestPostDTO DTO);

    void deletePost(Long postId);
}
