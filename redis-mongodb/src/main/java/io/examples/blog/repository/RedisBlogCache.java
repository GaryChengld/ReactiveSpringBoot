package io.examples.blog.repository;

import io.examples.blog.domain.Blog;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Component
public class RedisBlogCache {
    private final ReactiveRedisOperations<String, Blog> bolgOperations;
    private static final String KEY_BLOG = "Blog";

    public RedisBlogCache(ReactiveRedisOperations<String, Blog> bolgOperations) {
        this.bolgOperations = bolgOperations;
    }

    public Mono<Blog> findById(String id) {
        return bolgOperations.opsForValue().get(KEY_BLOG + ":" + id);
    }

    public Mono<Blog> save(Blog blog) {
        return bolgOperations.opsForValue().set(KEY_BLOG + ":" + blog.getId(), blog)
                .map(b -> blog);
    }

    public Mono<Void> deleteById(String id) {
        return bolgOperations.opsForValue().delete(KEY_BLOG + ":" + id)
                .flatMap(p -> Mono.empty());
    }
}
