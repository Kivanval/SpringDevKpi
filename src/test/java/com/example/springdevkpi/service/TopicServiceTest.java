package com.example.springdevkpi.service;

import com.example.springdevkpi.data.PostRepository;
import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.web.data.transfer.PostAddPayload;
import com.example.springdevkpi.web.data.transfer.TopicAddPayload;
import com.example.springdevkpi.web.data.transfer.TopicUpdatePayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.springdevkpi.service.TestHelper.getPostForTest;
import static com.example.springdevkpi.service.TestHelper.getUserForTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TopicServiceTest {

    @Autowired
    private TopicService topicService;

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
        userRepository.save(getUserForTest());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreate() {
        var payload = new TopicAddPayload("test", "test", "TESTER");

        var topic = topicService.create(payload);

        assertTrue(topicRepository.existsById(topic.getId()));
    }

    @Test
    void shouldSetFieldsFromPayload() {
        var payload = new TopicAddPayload("test", "test", "TESTER");

        var topic = topicService.create(payload);

        assertEquals("test", topic.getTitle());
        assertEquals("test", topic.getDescription());
        assertEquals("TESTER", topic.getCreator().getUsername());
    }


    @Test
    void shouldFindById() {
        var payload = new TopicAddPayload("test", "test", "TESTER");

        var topic = topicService.create(payload);

        assertTrue(topicService.findById(topic.getId()).isPresent());
    }

    @Test
    void shouldNotFindById() {
        var payload = new TopicAddPayload("test", "test", "TESTER");

        var topic = topicService.create(payload);

        assertTrue(topicService.findById(topic.getId() - 1).isEmpty());
    }

    @Test
    void shouldFindAll() {
        var payload1 = new TopicAddPayload("test", "test", "TESTER");
        var payload2 = new TopicAddPayload("test", "test", "TESTER");

        topicService.create(payload1);
        topicService.create(payload2);

        assertEquals(2, topicService.findAll(0, 2, "id").getSize());
    }

    @Test
    void shouldDeleteById() {
        var payload = new TopicAddPayload("test", "test", "TESTER");

        var topic = topicService.create(payload);
        topicService.deleteById(topic.getId());

        assertFalse(topicRepository.existsById(topic.getId()));
    }

    @Test
    void shouldDeleteByIdAllRelatedPosts() {
        var payload = new TopicAddPayload("test", "test", "TESTER");
        var post = getPostForTest();

        var topic = topicService.create(payload);
        topic.addPost(post);
        topicService.deleteById(topic.getId());

        assertEquals(0, postRepository.count());
        assertTrue(userRepository.existsByUsername("TESTER"));
    }


    @Test
    void shouldUpdateTitleAndDescFromPayload() {
        var payload = new TopicAddPayload("test", "test", "TESTER");
        var updatePayload = new TopicUpdatePayload("new test", "new test");

        var topic = topicService.create(payload);
        var isUpdated = topicService.update(updatePayload, topic.getId());
        var updatedTopic = topicService.findById(topic.getId())
                .orElseThrow(AssertionError::new);

        assertTrue(isUpdated);
        assertEquals("new test", updatedTopic.getTitle());
        assertEquals("new test", updatedTopic.getDescription());
    }
}