package io.examples.r2dbc.repository;

import io.examples.r2dbc.domain.Blog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String>, BlogSearchOperations {
    /**
     * Find blog by author
     *
     * @param author
     * @return
     */
    Flux<Blog> findByAuthor(String author);
}
