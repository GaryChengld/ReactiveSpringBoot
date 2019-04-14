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
        return blogRepository.findById(id);
    }

    public Mono<Blog> save(Blog blog) {
        log.debug("Save blog, blog:{}", blog);
        return blogRepository.save(blog);
    }

    public Mono<Void> deleteById(String id) {
        log.debug("Delete blog by id, id:{}", id);
        return blogRepository.deleteById(id);
    }

    public Flux<Blog> search(String text) {
        log.debug("Search blogs, search text:{}", text);
        return blogRepository.searchByKeyword(text);
    }
}
