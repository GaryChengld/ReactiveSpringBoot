package io.examples.r2dbc.repository;

import io.examples.r2dbc.domain.Blog;
import reactor.core.publisher.Flux;

public interface BlogSearchOperations {
    Flux<Blog> searchByKeyword(String keyword);
}
