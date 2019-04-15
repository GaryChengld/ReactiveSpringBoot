package io.examples.blog.repository;

import io.examples.blog.domain.Blog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Component
@Slf4j
public class RedisBlogCache {
    private final ReactiveRedisOperations<String, Blog> bolgOperations;
    private static final String KEY_BLOG = "Blog";

    public RedisBlogCache(ReactiveRedisOperations<String, Blog> bolgOperations) {
        this.bolgOperations = bolgOperations;
    }

    public Mono<Blog> get(String id) {
        log.debug("get Blog from cache, id:{}", id);
        return bolgOperations.opsForValue().get(KEY_BLOG + ":" + id);
    }

    public Mono<Blog> set(Blog blog) {
        log.debug("Set blog to cache, blog:{}", blog);
        return bolgOperations.opsForValue().set(KEY_BLOG + ":" + blog.getId(), blog)
                .map(b -> blog);
    }

}
