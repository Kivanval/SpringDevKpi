package com.example.springdevkpi.service;

import com.example.springdevkpi.data.PostRepository;
import com.example.springdevkpi.data.TopicRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.Post;
import com.example.springdevkpi.web.data.transfer.PostAddPayload;
import com.example.springdevkpi.web.data.transfer.PostUpdatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public Post create(PostAddPayload payload) {
        var optCreator = userRepository.findByUsername(payload.getCreatorUsername());
        if (optCreator.isEmpty()) {
            log.warn("Creator by username {} doesn't exists", payload.getCreatorUsername());
        }
        var optTopic = topicRepository.findById(payload.getTopicId());
        if (optTopic.isEmpty()) {
            log.warn("Topic by id {} doesn't exists", payload.getTopicId());
        }
        var post = new Post();
        post.setCreator(optCreator.orElseThrow());
        post.setTopic(optTopic.orElseThrow());
        post.setText(payload.getText().strip());
        postRepository.save(post);
        return postRepository.save(post);
    }

    @Transactional
    public boolean update(PostUpdatePayload payload, long id) {
        var optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            var post = optPost.get();
            post.setText(payload.getText().strip());
            postRepository.save(post);
            return true;
        }
        log.warn("Post by id {} doesn't exists", id);
        return false;
    }

    @Transactional
    public Page<Post> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(sortBy).descending());
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        if (postRepository.existsById(id))
            postRepository.deleteById(id);
    }

}
