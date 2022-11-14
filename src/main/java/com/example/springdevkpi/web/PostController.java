package com.example.springdevkpi.web;

import com.example.springdevkpi.service.PostService;
import com.example.springdevkpi.web.data.transfer.PostAddPayload;
import com.example.springdevkpi.web.data.transfer.PostPayload;
import com.example.springdevkpi.web.data.transfer.PostUpdatePayload;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    private static final String POST_PROPERTIES = "id|createdAt|creatorId|topicId";

    @GetMapping
    public ResponseEntity<List<PostPayload>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "id") @Pattern(regexp = POST_PROPERTIES) final String sortBy) {
        return ResponseEntity.ok(postService.findAll(page, size, sortBy).get()
                .map(post -> modelMapper.map(post, PostPayload.class))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostPayload> getById(
            @PathVariable @Min(1) final long id) {
        var optPost = postService.findById(id);
        return optPost.map(post -> ResponseEntity.ok(modelMapper.map(post, PostPayload.class)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<PostPayload> addOne(
            @RequestBody @Valid final PostAddPayload payload) {
        return postService.create(payload) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostPayload> delete(
            @PathVariable @Min(1) final long id) {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostPayload> update(
            @RequestBody @Valid final PostUpdatePayload payload,
            @PathVariable @Min(1) final long id) {
        return postService.update(payload, id) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
