package com.example.springdevkpi.service;

import com.example.springdevkpi.data.PostRepository;
import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.web.data.transfer.PostAddPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        topicRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void shouldCreatePostsWithPayloadFieldsAndFindAll() {
        for (int i = 0; i < 2; i++) {
            var payload = new PostAddPayload("TEST", "TEST", 1L);
            postService.create(payload);
        }
        var posts = postService.findAll(0, 2, "id");
        assertEquals(2, posts.getSize());
    }

    @Test
    void shouldNotFind() {
        var payload = new PostAddPayload("TEST", "TESTER", 1L);
        postService.create(payload);
        assertTrue(postService.findById(27L).isEmpty());
    }

    @Test
    void shouldDeleteById() {
        var payload = new PostAddPayload("TEST", "TESTER", 1L);
        postService.create(payload);
        var posts = postService.findAll(1,1,"id");
        var post = posts.get().findFirst();
        assertTrue(post.isPresent());
        var postId = posts.get().findFirst().get().getId();
        assertTrue(postService.findById(postId).isPresent());
    }
}
