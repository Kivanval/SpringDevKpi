package com.example.springdevkpi.service;

import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.domain.Topic;
import com.example.springdevkpi.domain.User;
import com.example.springdevkpi.web.dto.TopicBasePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic createFrom(TopicBasePayload payload, User creator) {
        var topic = new Topic();
        topic.setTitle(payload.title());
        topic.setDescription(payload.description());
        topic.setCreator(creator);
        return topic;
    }

    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }

    public Page<Topic> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }


}
