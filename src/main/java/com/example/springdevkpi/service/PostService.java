package com.example.springdevkpi.service;

import com.example.springdevkpi.data.PostRepository;
import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.Post;
import com.example.springdevkpi.data.transfer.PostBasePayload;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostService {

    @Delegate
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public boolean create(PostBasePayload payload) {
        var optCreator = userRepository.findByUsername(payload.getCreatorUsername());
        if (optCreator.isEmpty()) {
            log.warn("Creator by username {} doesn't exists", payload.getCreatorUsername());
            return false;
        }
        var optTopic = topicRepository.findById(payload.getTopicId());
        if (optTopic.isEmpty()) {
            log.warn("Topic by id {} doesn't exists", payload.getTopicId());
            return false;
        }
        var post = new Post();
        post.setText(payload.getText());
        postRepository.save(post);
        return true;
    }
}
