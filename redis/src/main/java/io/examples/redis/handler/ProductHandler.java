package io.examples.redis.handler;

import io.examples.redis.domain.Product;
import io.examples.redis.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@Slf4j
public class ProductHandler {
    private final ProductRepository productRepository;

    public ProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public RouterFunction<ServerResponse> getRouterFunction() {
        return nest(accept(APPLICATION_JSON),
                route(GET("/"), this::all)
                        .andRoute(GET("/{id}"), this::byId)
                        .andRoute(POST("/"), this::create)
                        .andRoute(PUT("/{id}"), this::update)
                        .andRoute(DELETE("/{id}"), this::delete)
        );
    }

    private Mono<ServerResponse> all(ServerRequest request) {
        log.debug("Received find all request");
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(this.productRepository.findAll(), Product.class);
    }

    private Mono<ServerResponse> byId(ServerRequest request) {
        log.debug("Received find by id request");
        return this.productRepository.findById(request.pathVariable("id"))
                .flatMap(this::buildResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> create(ServerRequest request) {
        log.debug("Received create request");
        return request.bodyToMono(Product.class)
                .flatMap(productRepository::save)
                .flatMap(this::buildResponse);
    }

    private Mono<ServerResponse> update(ServerRequest request) {
        log.debug("Received update request");
        AtomicReference<Product> productRef = new AtomicReference<>();
        return request.bodyToMono(Product.class)
                .doOnNext(productRef::set)
                .flatMap(p -> this.productRepository.findById(request.pathVariable("id")))
                .map(p -> {
                    p.setName(productRef.get().getName());
                    p.setCategory(productRef.get().getCategory());
                    return p;
                })
                .flatMap(this.productRepository::save)
                .flatMap(this::buildResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> delete(ServerRequest request) {
        log.debug("Received delete request");
        return ServerResponse.noContent().build(this.productRepository.deleteById(request.pathVariable("id")));
    }

    private <T> Mono<ServerResponse> buildResponse(T body) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(fromObject(body));
    }
}
