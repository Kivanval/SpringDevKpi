package com.example.springdevkpi.data;

import com.example.springdevkpi.domain.Post;
import com.example.springdevkpi.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    void deleteByCreator(User creator);
    List<Post> findAllByCreator(User creator);
}

