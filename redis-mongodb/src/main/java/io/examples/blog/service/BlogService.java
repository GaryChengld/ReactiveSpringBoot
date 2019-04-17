package io.examples.blog.service;

import io.examples.blog.domain.Blog;
import io.examples.blog.repository.BlogRepository;
import io.examples.blog.repository.RedisBlogCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Service
@Slf4j
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private RedisBlogCache redisBlogCache;

    public Flux<Blog> findAll() {
        log.debug("Find all blogs");
        return blogRepository.findAll();
    }

    public Flux<Blog> findByAuthor(String author) {
        log.debug("Find blogs by authors, authors:{}", author);
        return blogRepository.findByAuthor(author);
    }

    public Mono<Blog> findById(String id) {
        log.debug("Find blog by id, id:{}", id);
        return redisBlogCache.get(id)
                .switchIfEmpty(Mono.defer(() -> this.findByIdFromDB(id)))
                .filter(b -> null != b.getId());
    }

    public Mono<Blog> save(Blog blog) {
        log.debug("Save blog, blog:{}", blog);
        return blogRepository.save(blog)
                .flatMap(b -> redisBlogCache.set(b.getId(), b));
    }

    public Mono<Void> deleteById(String id) {
        log.debug("Delete blog by id, id:{}", id);
        return blogRepository.deleteById(id)
                .then(Mono.defer(() -> this.redisBlogCache.set(id, new Blog())))
                .thenEmpty(Mono.empty());
    }

    public Flux<Blog> search(String text) {
        log.debug("Search blogs, search text:{}", text);
        return blogRepository.searchByKeyword(text);
    }

    private Mono<Blog> findByIdFromDB(String id) {
        log.debug("Load blog from DB");
        return this.blogRepository.findById(id)
                .defaultIfEmpty(new Blog())
                .flatMap(b -> redisBlogCache.set(id, b));
    }
}
