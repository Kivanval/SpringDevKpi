package com.example.springdevkpi.service;

import com.example.springdevkpi.data.PostRepository;
import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.web.data.transfer.PostAddPayload;
import com.example.springdevkpi.web.data.transfer.PostUpdatePayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.springdevkpi.service.TestHelper.getTopicForTest;
import static com.example.springdevkpi.service.TestHelper.getUserForTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
        topicRepository.save(getTopicForTest(userRepository.save(getUserForTest())));
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreate() {
        var topic = topicRepository.findAll().iterator().next();
        var payload = new PostAddPayload("test", "TESTER", topic.getId());

        var post = postService.create(payload);

        assertTrue(postRepository.existsById(post.getId()));
    }

    @Test
    void shouldSetFieldsFromPayload() {
        var topic = topicRepository.findAll().iterator().next();
        var payload = new PostAddPayload(" test ", "TESTER", topic.getId());

        var post = postService.create(payload);

        assertEquals("test", post.getText());
        assertEquals("TESTER", post.getCreator().getUsername());
    }

    @Test
    void shouldFindById() {
        var topic = topicRepository.findAll().iterator().next();
        var payload = new PostAddPayload("test", "TESTER", topic.getId());

        var post = postService.create(payload);

        assertTrue(postService.findById(post.getId()).isPresent());
    }


    @Test
    void shouldNotFindByName() {
        var topic = topicRepository.findAll().iterator().next();
        var payload = new PostAddPayload("test", "TESTER", topic.getId());

        var post = postService.create(payload);

        assertFalse(postService.findById(post.getId() - 1).isPresent());
    }


    @Test
    void shouldFindAll() {
        var topic = topicRepository.findAll().iterator().next();
        var payload1 = new PostAddPayload("test", "TESTER", topic.getId());
        var payload2 = new PostAddPayload("test", "TESTER", topic.getId());

        postService.create(payload1);
        postService.create(payload2);

        assertEquals(2, postService.findAll(0, 2, "id").getSize());
    }


    @Test
    void shouldDeleteById() {
        var topic = topicRepository.findAll().iterator().next();
        var payload = new PostAddPayload("test", "TESTER", topic.getId());

        var post = postService.create(payload);
        postService.deleteById(post.getId());

        assertFalse(postRepository.existsById(post.getId()));
        assertTrue(topicRepository.existsById(topic.getId()));
        assertTrue(userRepository.existsByUsername("TESTER"));
    }

    @Test
    void shouldUpdateTextFromPayload() {
        var topic = topicRepository.findAll().iterator().next();
        var payload = new PostAddPayload("test", "TESTER", topic.getId());
        var updatePayload = new PostUpdatePayload(" new test ");

        var post = postService.create(payload);
        var isUpdated = postService.update(updatePayload, post.getId());
        var updatedPost = postRepository.findById(post.getId()).orElseThrow();

        assertTrue(isUpdated);
        assertEquals("new test", updatedPost.getText());
    }
}