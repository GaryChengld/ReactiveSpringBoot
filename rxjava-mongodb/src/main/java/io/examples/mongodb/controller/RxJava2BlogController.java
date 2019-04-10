package io.examples.mongodb.controller;

import io.examples.mongodb.domain.Blog;
import io.examples.mongodb.repository.BlogRepository;
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

    @GetMapping(value = "/byAuthor/{author}")
    public Flowable<Blog> byAuthor(@PathVariable("author") String author) {
        log.debug("Received byAuthor() request, author:{}", author);
        return this.blogRepository.findByAuthor(author);
    }

    @GetMapping(value = "/search/{text}")
    public Flowable<Blog> search(@PathVariable("text") String text) {
        log.debug("Received search() request, text:{}", text);
        return this.blogRepository.searchByKeyword(text);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Single<Blog> create(@RequestBody Blog blog) {
        log.debug("Received create() request, blog:{}", blog);
        return this.blogRepository.save(blog);
    }

    @DeleteMapping(value = "/{id}")
    public Maybe<ResponseEntity> delete(@PathVariable("id") String id) {
        log.debug("Received delete() request, id:{}", id);
        return blogRepository.findById(id)
                .flatMap(this::deleteBlog)
                .<ResponseEntity>map(b -> ResponseEntity.noContent().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Maybe<ResponseEntity<?>> update(@PathVariable("id") String id, @RequestBody Blog blog) {
        log.debug("Received update() request, blog:{}", blog);
        return blogRepository.findById(id)
                .flatMap(b -> {
                    b.setTitle(blog.getTitle());
                    b.setContent(blog.getContent());
                    b.setAuthor(blog.getAuthor());
                    return this.blogRepository.save(b).toMaybe();
                })
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Maybe<Boolean> deleteBlog(Blog blog) {
        return Maybe.create(emitter ->
                this.blogRepository.delete(blog).subscribe(() -> emitter.onSuccess(true), emitter::onError));
    }
}
