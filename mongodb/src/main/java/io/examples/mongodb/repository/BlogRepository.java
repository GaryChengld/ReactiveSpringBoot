package io.examples.mongodb.repository;

import io.examples.mongodb.domain.Blog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
}
