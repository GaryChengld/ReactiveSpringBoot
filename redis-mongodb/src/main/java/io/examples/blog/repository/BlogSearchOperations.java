package io.examples.blog.repository;

import io.examples.blog.domain.Blog;
import reactor.core.publisher.Flux;

public interface BlogSearchOperations {
    Flux<Blog> searchByKeyword(String keyword);
}
