package io.examples.redis.repository;

import io.examples.redis.domain.Product;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Gary Cheng
 */
@Repository
public class ProductRepository {
    private final ReactiveRedisOperations<String, Product> productOperations;
    private static final String KEY_PRODUCTS = "products";

    public ProductRepository(ReactiveRedisOperations<String, Product> productOperations) {
        this.productOperations = productOperations;
    }

    public Flux<Product> findAll() {
        return productOperations.<String, Product>opsForHash()
                .values(KEY_PRODUCTS);
    }

    public Mono<Product> findById(String id) {
        return productOperations.<String, Product>opsForHash()
                .get(KEY_PRODUCTS, id);
    }

    public Mono<Product> save(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        return productOperations.<String, Product>opsForHash()
                .put(KEY_PRODUCTS, product.getId(), product)
                .map(p -> product);
    }

    public Mono<Void> deleteById(String id) {
        return productOperations.<String, Product>opsForHash().remove(KEY_PRODUCTS, id)
                .flatMap(p -> Mono.empty());
    }
}
