package com.example.springdevkpi.service;

import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.Topic;
import com.example.springdevkpi.data.transfer.TopicBasePayload;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TopicService {

    @Delegate
    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    public boolean create(TopicBasePayload payload) {
        var optCreator = userRepository.findByUsername(payload.getCreatorUsername());
        if (optCreator.isEmpty()) {
            log.warn("Creator by username {} doesn't exists", payload.getCreatorUsername());
            return false;
        }
        var topic = new Topic();
        topic.setTitle(payload.getTitle());
        topic.setDescription(payload.getDescription());
        topic.setCreator(optCreator.get());
        topicRepository.save(topic);
        return true;
    }


}
