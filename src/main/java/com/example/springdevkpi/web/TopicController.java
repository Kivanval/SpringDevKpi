package com.example.springdevkpi.web;

import com.example.springdevkpi.service.TopicService;
import com.example.springdevkpi.data.transfer.PostPayload;
import com.example.springdevkpi.data.transfer.TopicBasePayload;
import com.example.springdevkpi.data.transfer.TopicPayload;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    private final ModelMapper modelMapper;

    @Autowired
    public TopicController(TopicService topicService, ModelMapper modelMapper) {
        this.topicService = topicService;
        this.modelMapper = modelMapper;
    }

    private static final  String TOPIC_PROPERTIES = "id|title|createdAt|creatorId";

    @GetMapping("/")
    public Collection<TopicPayload> getTopics(
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "id") @Pattern(regexp = TOPIC_PROPERTIES) final String sortBy) {
        return topicService.findAll(PageRequest.of(page, size)
                        .withSort(Sort.by(sortBy)))
                .stream()
                .map(topic -> modelMapper.map(topic, TopicPayload.class))
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicPayload> getTopicById(
            @PathVariable @Min(1) final long id) {
        var optTopic = topicService.findById(id);
        return optTopic.map(topic -> ResponseEntity.ok(modelMapper.map(topic, TopicPayload.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Set<PostPayload>> getPostsByUserId(
            @PathVariable @Min(1) final long id) {
        var optTopic = topicService.findById(id);
        return optTopic.map(topic -> ResponseEntity.ok(topic.getPosts().stream()
                        .map(post -> modelMapper.map(post, PostPayload.class))
                        .collect(Collectors.toSet())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/")
    public ResponseEntity<TopicPayload> addTopic(
            @RequestBody @Valid final TopicBasePayload payload) {
        return topicService.create(payload) ?
                ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TopicPayload> deleteTopic(
            @PathVariable @Min(1) final long id) {
        topicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
