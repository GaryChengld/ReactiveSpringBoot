package io.examples.blog.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public Mono<Blog> set(String id, Blog blog) {
        log.debug("Set blog to cache, blog:{}", blog);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.debug(objectMapper.writeValueAsString(blog));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bolgOperations.opsForValue().set(KEY_BLOG + ":" + id, blog)
                .map(b -> blog);
    }
}
