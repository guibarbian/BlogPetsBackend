package com.db.BlogPetsBackend.Unit;

import com.db.BlogPetsBackend.DTO.Request.RequestPostDTO;
import com.db.BlogPetsBackend.DTO.Response.ResponsePostDTO;
import com.db.BlogPetsBackend.Exceptions.NotFoundException;
import com.db.BlogPetsBackend.Models.Post;
import com.db.BlogPetsBackend.Repositories.PostRepository;
import com.db.BlogPetsBackend.Services.IMPL.PostServiceImpl;
import com.db.BlogPetsBackend.mapper.PostMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    Post validPost = new Post(
            null, "Hey Guys",
            "This is my first post!", 0
    );
    Post validPostId = new Post(
            1L, "Hey Guys",
            "This is my first post!", 0
    );

    ResponsePostDTO validPostIdDto= new ResponsePostDTO(
            validPostId.getId(), validPostId.getTitle(), validPostId.getContent(), validPostId.getLikes()
    );

    @Test
    public void testGetPostById(){
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPostId));
        when(postMapper.postToDto(validPostId)).thenReturn(validPostIdDto);

        ResponsePostDTO responsePostDTO = postService.getPostById(1L);
    }

    @Test
    public void testGetPostByIdThrowsNotFound(){
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            postService.getPostById(1L);
        });

        assertEquals("Post not found with id: 1", exception.getMessage());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllPosts(){
        when(postRepository.findAll()).thenReturn(List.of(validPostId));
        when(postMapper.postToDto(validPostId)).thenReturn(validPostIdDto);

        List<ResponsePostDTO> response = postService.getAllPosts();

        assertEquals(1, response.size());
        assertEquals("Hey Guys", response.getFirst().title());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testCreateValidPost(){
        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "Hey Guys", "This is my first post!", 0
        );

        when(postRepository.save(validPost)).thenReturn(validPostId);
        when(postMapper.postToDto(validPostId)).thenReturn(validPostIdDto);

        ResponsePostDTO response = postService.createPost(requestPostDTO);

        assertEquals(validPostIdDto, response);
        verify(postRepository, times(1)).save(validPost);
    }

    @Test
    public void testUpdatePost(){
        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "Hey Guys", "This is my second post!", 12
        );

        Post updatedPostId = new Post(
                1L, requestPostDTO.title(),
                requestPostDTO.content(), requestPostDTO.likes()
        );

        Post updatedPost = new Post(
                1L, requestPostDTO.title(),
                requestPostDTO.content(), requestPostDTO.likes()
        );

        ResponsePostDTO responseDto = new ResponsePostDTO(
                updatedPostId.getId(), updatedPost.getTitle(),
                updatedPost.getContent(), updatedPost.getLikes()
        );

        when(postRepository.findById(1L)).thenReturn(Optional.of(validPostId));
        when(postRepository.save(updatedPost)).thenReturn(updatedPostId);
        when(postMapper.postToDto(updatedPost)).thenReturn(responseDto);

        ResponsePostDTO responsePostDTO = postService.updatePost(1L, requestPostDTO);

        assertEquals(1L, responsePostDTO.id());
        assertEquals("Hey Guys", responsePostDTO.title());
        assertEquals("This is my second post!", responsePostDTO.content());
        assertEquals(12, responsePostDTO.likes());
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(updatedPost);
    }

    @Test
    public void testDeletePost(){
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPostId));

        postService.deletePost(1L);

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).delete(validPostId);
        assertEquals(0, postRepository.findAll().size());
    }
}
