package io.examples.mongodb.repository;

import io.examples.mongodb.domain.Blog;
import io.reactivex.Flowable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BlogRepository extends RxJava2CrudRepository<Blog, String>, BlogSearchOperations {
    /**
     * Find blog by author
     *
     * @param author
     * @return
     */
    Flowable<Blog> findByAuthor(String author);
}
