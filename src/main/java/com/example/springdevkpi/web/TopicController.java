package com.example.springdevkpi.web;

import com.example.springdevkpi.service.TopicService;
import com.example.springdevkpi.web.data.transfer.PostPayload;
import com.example.springdevkpi.web.data.transfer.TopicAddPayload;
import com.example.springdevkpi.web.data.transfer.TopicPayload;
import com.example.springdevkpi.web.data.transfer.TopicUpdatePayload;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Comparator;
import java.util.List;

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

    private static final String TOPIC_PROPERTIES = "id|title|createdAt|creatorId";

    @GetMapping("/")
    public ResponseEntity<List<TopicPayload>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "id") @Pattern(regexp = TOPIC_PROPERTIES) final String sortBy) {
        return ResponseEntity.ok(topicService.findAll(page, size, sortBy)
                .stream()
                .map(topic -> modelMapper.map(topic, TopicPayload.class))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicPayload> getById(
            @PathVariable @Min(1) final long id) {
        var optTopic = topicService.findById(id);
        return optTopic.map(topic -> ResponseEntity.ok(modelMapper.map(topic, TopicPayload.class)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostPayload>> getByUserId(
            @PathVariable @Min(1) final long id) {
        var optTopic = topicService.findById(id);
        return optTopic.map(topic -> ResponseEntity.ok(topic.getPosts().stream()
                        .map(post -> modelMapper.map(post, PostPayload.class))
                        .sorted(Comparator.comparing(PostPayload::getCreatedAt).reversed())
                        .toList()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/")
    public ResponseEntity<TopicPayload> addOne(
            @RequestBody @Valid final TopicAddPayload payload) {
        return topicService.create(payload) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TopicPayload> delete(
            @PathVariable @Min(1) final long id) {
        topicService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TopicPayload> update(
            @RequestBody @Valid final TopicUpdatePayload payload,
            @PathVariable @Min(1) final long id) {
        return topicService.update(payload, id) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


}
