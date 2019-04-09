package io.examples.mongodb.controller;

import io.examples.mongodb.domain.Blog;
import io.examples.mongodb.repository.BlogRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/v1/blogs")
@Slf4j
public class RxJava2BlogController {
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping
    public Flowable<Blog> all() {
        log.debug("Received all() request");
        return this.blogRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Maybe<ResponseEntity<?>> byId(@PathVariable("id") String id) {
        log.debug("Received byId() request, id:{}", id);
        return blogRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
