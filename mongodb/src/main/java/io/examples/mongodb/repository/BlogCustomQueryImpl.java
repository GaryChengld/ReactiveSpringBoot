package io.examples.mongodb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Gary Cheng
 */

@Component
public class BlogCustomQueryImpl implements BlogCustomQuery{
    @Autowired
    ReactiveMongoTemplate template;
}
