package io.examples.r2dbc.controller;

import io.examples.r2dbc.domain.Blog;
import io.examples.r2dbc.repository.BlogRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/v1/blogs")
@Slf4j
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping
    public Flowable<Blog> all() {
        log.debug("Received all() request");
        return this.blogRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Single<ResponseEntity<?>> byId(@PathVariable("id") Integer id) {
        log.debug("Received byId() request, id:{}", id);
        return blogRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .toSingle();
    }

    @GetMapping(value = "/byAuthor/{author}")
    public Flowable<Blog> byAuthor(@PathVariable("author") String author) {
        log.debug("Received byAuthor() request, author:{}", author);
        return this.blogRepository.findByAuthor(author);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Single<Blog> create(@RequestBody Blog blog) {
        log.debug("Received create() request, blog:{}", blog);
        return this.blogRepository.save(blog);
    }

    @DeleteMapping(value = "/{id}")
    public Single<ResponseEntity> delete(@PathVariable("id") Integer id) {
        log.debug("Received delete() request, id:{}", id);
        return blogRepository.findById(id)
                .flatMap(this::deleteBlog)
                .<ResponseEntity>map(b -> ResponseEntity.noContent().build())
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .toSingle();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<?>> update(@PathVariable("id") Integer id, @RequestBody Blog blog) {
        log.debug("Received update() request, blog:{}", blog);
        return blogRepository.findById(id)
                .flatMap(b -> {
                    b.setTitle(blog.getTitle());
                    b.setContent(blog.getContent());
                    b.setAuthor(blog.getAuthor());
                    return this.blogRepository.save(b).toMaybe();
                })
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .toSingle();
    }

    private Maybe<Boolean> deleteBlog(Blog blog) {
        return Maybe.create(emitter ->
                this.blogRepository.delete(blog).subscribe(() -> emitter.onSuccess(Boolean.TRUE), emitter::onError));
    }
}
