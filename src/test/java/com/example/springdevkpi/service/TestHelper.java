package com.example.springdevkpi.service;

import com.example.springdevkpi.domain.Post;
import com.example.springdevkpi.domain.Topic;
import com.example.springdevkpi.domain.User;

public final class TestHelper {
    private TestHelper() {

    }

    static User getUserForTest() {
        var user = new User();
        user.setUsername("TESTER");
        user.setPassword("TESTER");
        return user;
    }

    static Topic getTopicForTest(User creator) {
        var topic = new Topic();
        topic.setId(1L);
        topic.setTitle("");
        topic.setDescription("");
        topic.setCreator(creator);
        return topic;
    }

    static Post getPostForTest() {
        var post = new Post();
        post.setText("test");
        return post;
    }
}
