package com.db.BlogPetsBackend.Integration;

import com.db.BlogPetsBackend.DTO.Request.RequestPostDTO;
import com.db.BlogPetsBackend.Models.Post;
import com.db.BlogPetsBackend.Repositories.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    String baseUrl = "/api/v1/posts";

    @BeforeEach
    public void setup() throws Exception{
        postRepository.deleteAll();
    }

    @Test
    void testShallGetPostById() throws Exception{
        Post post = new Post(
                null,
                "Hey Guys",
                "This is my first post!",
                0,
                true
        );

        Post savedPost = postRepository.save(post);

        mockMvc.perform(get(baseUrl + "/" + savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Hey Guys"))
                .andExpect(jsonPath("$.content").value("This is my first post!"))
                .andExpect(jsonPath("$.likes").value(0))
                .andExpect(jsonPath("$.liked").value(true));
    }

    @Test
    void testShallThrowExcpetionWhenPostNotFound() throws Exception {
        mockMvc.perform(get( baseUrl + "/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShallGetAllPosts() throws Exception{
        Post post1 = new Post(
                null,
                "First Post",
                "Content of the first post",
                0,
                false
        );
        Post post2 = new Post(
                null,
                "Second Post",
                "Content of the second post",
                0,
                true
        );

        postRepository.save(post1);
        postRepository.save(post2);

        mockMvc.perform(get(baseUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("First Post"))
                .andExpect(jsonPath("$[1].title").value("Second Post"));
    }

    @Test
    void shallCreatePost() throws Exception{
        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "Hey Guys",
                "This is my first post!",
                12,
                true
        );

        mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestPostDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Hey Guys"))
                .andExpect(jsonPath("$.content").value("This is my first post!"))
                .andExpect(jsonPath("$.likes").value(0))
                .andExpect(jsonPath("$.liked").value(false));
    }

    @Test
    void shallUpdatePost() throws Exception{
        Post post = new Post(
                null,
                "Hey Guys",
                "This is my first post!",
                0,
                false
        );

        Post savedPost = postRepository.save(post);

        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "Ok Computer",
                "Do you guys like Radiohead?",
                5,
                true
        );

        mockMvc.perform(put(baseUrl + "/" + savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestPostDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Ok Computer"))
                .andExpect(jsonPath("$.content").value("Do you guys like Radiohead?"))
                .andExpect(jsonPath("$.likes").value(5))
                .andExpect(jsonPath("$.liked").value(true));
    }

    @Test
    void testShallThrowNotFounWhenUpdate() throws Exception{
        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "Ok Computer",
                "Do you guys like Radiohead?",
                5,
                false
        );

        mockMvc.perform(put(baseUrl + "/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestPostDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Post not found with id: 999999"));
    }

    @Test
    void testShallDeletePost() throws Exception{
        Post post = new Post(
                null,
                "Hey Guys",
                "This is my first post!",
                0,
                true
        );

        Post savedPost = postRepository.save(post);

        mockMvc.perform(delete(baseUrl + "/" + savedPost.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Post deleted"));
    }

    @Test
    void testShallThrowNotFoundWhenDelete() throws Exception {
        mockMvc.perform(delete(baseUrl + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Post not found with id: 999999"));
    }
}
