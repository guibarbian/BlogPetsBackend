package com.db.BlogPetsBackend.Services.IMPL;

import com.db.BlogPetsBackend.DTO.Request.RequestPostDTO;
import com.db.BlogPetsBackend.DTO.Response.ResponsePostDTO;
import com.db.BlogPetsBackend.Exceptions.NotFoundException;
import com.db.BlogPetsBackend.Models.Post;
import com.db.BlogPetsBackend.Repositories.PostRepository;
import com.db.BlogPetsBackend.Services.PostService;
import com.db.BlogPetsBackend.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;


    @Override
    public ResponsePostDTO getPostById(Long postId) {
        return postMapper.postToDto(findPostById(postId));
    }

    @Override
    public List<ResponsePostDTO> getAllPosts(){
        return postRepository.findAll().stream()
                .map(postMapper::postToDto)
                .toList();
    }

    @Override
    public ResponsePostDTO createPost(RequestPostDTO DTO) {
        return postMapper.postToDto(
                postRepository.save(createPostBody(DTO))
        );
    }

    @Override
    public ResponsePostDTO updatePost(Long postId, RequestPostDTO DTO) {
        return postMapper.postToDto(
                postRepository.save(createPostBody(postId, DTO))
        );
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.delete(findPostById(postId));
    }

    private Post findPostById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
    }

    private Post createPostBody(RequestPostDTO dto){
        return Post.builder()
                .title(dto.title())
                .content(dto.content())
                .likes(0)
                .build();
    }

    private Post createPostBody(Long postId, RequestPostDTO dto){
        findPostById(postId);

        return Post.builder()
                .id(postId)
                .title(dto.title())
                .content(dto.content())
                .likes(dto.likes())
                .build();
    }
}
