package io.examples.r2dbc.repository;

import io.examples.r2dbc.domain.Blog;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BlogRepository extends ReactiveCrudRepository<Blog, Integer> {
    /**
     * Find blog by author
     *
     * @param author
     * @return
     */
    @Query("SELECT * FROM blog WHERE author = $1")
    Flux<Blog> findByAuthor(String author);
}
