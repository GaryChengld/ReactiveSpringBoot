package io.examples.mongodb.repository;

import io.examples.mongodb.domain.Blog;
import io.reactivex.Flowable;

public interface BlogSearchOperations {
    Flowable<Blog> searchByKeyword(String keyword);
}
