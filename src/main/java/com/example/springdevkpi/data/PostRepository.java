package com.example.springdevkpi.data;

import com.example.springdevkpi.domain.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
