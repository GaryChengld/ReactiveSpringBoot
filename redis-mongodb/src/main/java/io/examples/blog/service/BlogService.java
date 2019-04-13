package io.examples.blog.service;

import io.examples.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gary Cheng
 */
@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
}
