package io.examples.mongodb.repository;

import io.examples.mongodb.domain.Blog;
import reactor.core.publisher.Flux;

public interface BlogSearchOperations {
    Flux<Blog> searchByKeyword(String keyword);
}
