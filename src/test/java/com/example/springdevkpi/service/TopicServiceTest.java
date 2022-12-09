package com.example.springdevkpi.service;


import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        topicRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        topicRepository.deleteAll();
    }


}
