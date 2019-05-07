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
public class BlogCache {
    private final ReactiveRedisOperations<String, Blog> bloOperations;
    private static final String KEY_BLOG = "Blog";

    public BlogCache(ReactiveRedisOperations<String, Blog> bloOperations) {
        this.bloOperations = bloOperations;
    }

    public Mono<Blog> get(String id) {
        log.debug("get Blog from cache, id:{}", id);
        return bloOperations.opsForValue().get(KEY_BLOG + ":" + id);
    }

    public Mono<Blog> set(String id, Blog blog) {
        log.debug("Set blog to cache, blog:{}", blog);
        return bloOperations.opsForValue().set(KEY_BLOG + ":" + id, blog)
                .map(b -> blog);
    }
}
