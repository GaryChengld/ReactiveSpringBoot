package io.examples.blog.handler;

import io.examples.blog.domain.Blog;
import io.examples.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@Slf4j
public class BlogHandler {
    private final BlogService blogService;

    public BlogHandler(BlogService blogService) {
        this.blogService = blogService;
    }

    public RouterFunction<ServerResponse> getRouterFunction() {
        return nest(accept(APPLICATION_JSON),
                route(GET("/"), this::all)
                        .andRoute(GET("/{id}"), this::byId)
                        .andRoute(GET("/byAuthor/{author}"), this::byAuthor)
                        .andRoute(GET("/search/{keyword}"), this::search)
                        .andRoute(POST("/"), this::create)
                        .andRoute(PUT("/{id}"), this::update)
                        .andRoute(DELETE("/{id}"), this::delete)
        );
    }

    private Mono<ServerResponse> all(ServerRequest request) {
        log.debug("Received find all blogs request");
        return this.buildResponse(this.blogService.findAll());
    }

    private Mono<ServerResponse> byId(ServerRequest request) {
        log.debug("Received find blog by id request");
        return this.blogService.findById(request.pathVariable("id"))
                .flatMap(this::buildResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> byAuthor(ServerRequest request) {
        log.debug("Received find blog by author request");
        return this.buildResponse(this.blogService.findByAuthor(request.pathVariable("author")));
    }

    private Mono<ServerResponse> search(ServerRequest request) {
        log.debug("Received find blog by author request");
        return this.buildResponse(this.blogService.search(request.pathVariable("keyword")));
    }

    private Mono<ServerResponse> create(ServerRequest request) {
        log.debug("Received create blog request");
        return request.bodyToMono(Blog.class)
                .flatMap(this.blogService::save)
                .flatMap(this::buildResponse);
    }

    private Mono<ServerResponse> update(ServerRequest request) {
        log.debug("Received update blog request");
        AtomicReference<Blog> blogRef = new AtomicReference<>();
        return request.bodyToMono(Blog.class)
                .doOnNext(blogRef::set)
                .flatMap(b -> this.blogService.findById(request.pathVariable("id")))
                .map(b -> {
                    Blog blog = blogRef.get();
                    b.setTitle(blog.getTitle());
                    b.setContent(blog.getContent());
                    b.setAuthor(blog.getAuthor());
                    return b;
                })
                .flatMap(this.blogService::save)
                .flatMap(this::buildResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> delete(ServerRequest request) {
        log.debug("Received delete blog request");
        return ServerResponse.noContent().build(this.blogService.deleteById(request.pathVariable("id")));
    }

    private <T> Mono<ServerResponse> buildResponse(T body) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(fromObject(body));
    }

    private Mono<ServerResponse> buildResponse(Flux<Blog> blogs) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(blogs, Blog.class);
    }
}
