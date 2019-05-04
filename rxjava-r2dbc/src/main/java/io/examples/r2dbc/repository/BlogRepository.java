package io.examples.r2dbc.repository;

import io.examples.r2dbc.domain.Blog;
import io.reactivex.Flowable;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends RxJava2CrudRepository<Blog, Integer> {
    /**
     * Find blog by author
     *
     * @param author
     * @return
     */
    @Query("SELECT * FROM blog WHERE author = $1")
    Flowable<Blog> findByAuthor(String author);
}
