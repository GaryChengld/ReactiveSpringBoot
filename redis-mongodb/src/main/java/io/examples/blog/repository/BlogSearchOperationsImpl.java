package io.examples.blog.repository;

import io.examples.blog.domain.Blog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author Gary Cheng
 */

@Component
@Slf4j
public class BlogSearchOperationsImpl implements BlogSearchOperations {
    @Autowired
    ReactiveMongoTemplate template;

    @Override
    public Flux<Blog> searchByKeyword(String keyword) {
        log.debug("Search blog by keyword:{}", keyword);
        TextQuery query = new TextQuery(new TextCriteria().matchingAny(keyword).caseSensitive(false)).sortByScore();
        return template.find(query, Blog.class);
    }
}
