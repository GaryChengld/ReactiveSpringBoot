package io.examples.mongodb.repository;

import io.examples.mongodb.domain.Blog;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

/**
 * @author Gary Cheng
 */

@Component
@Slf4j
public class BlogSearchOperationsImpl implements BlogSearchOperations {
    @Autowired
    ReactiveMongoTemplate template;

    @Override
    public Flowable<Blog> searchByKeyword(String keyword) {
        log.debug("Search blog by keyword:{}", keyword);
        TextQuery query = new TextQuery(new TextCriteria().matchingAny(keyword).caseSensitive(false)).sortByScore();
        return Flowable.fromPublisher(template.find(query, Blog.class));
    }
}
