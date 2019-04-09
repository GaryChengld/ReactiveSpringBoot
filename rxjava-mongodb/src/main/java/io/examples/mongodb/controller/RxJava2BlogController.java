package io.examples.mongodb.controller;

import io.examples.mongodb.domain.Blog;
import io.examples.mongodb.repository.BlogRepository;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/v1/blogs")
public class RxJava2BlogController {
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping(value = "")
    public Flowable<Blog> all() {
        return this.blogRepository.findAll();
    }
}
