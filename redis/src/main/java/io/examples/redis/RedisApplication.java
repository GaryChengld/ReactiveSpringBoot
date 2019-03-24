package io.examples.redis;

import io.examples.redis.domain.Product;
import io.examples.redis.handler.ProductHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;

/**
 * @author Gary Cheng
 */
@SpringBootApplication
@Configuration
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @Bean
    ReactiveRedisOperations<String, Product> redisOperations(ReactiveRedisConnectionFactory factory) {
        RedisSerializationContext<String, Product> context = RedisSerializationContext
                .<String, Product>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(Product.class))
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(
            ProductHandler productHandler) {
        return nest(path("/v1/product"), productHandler.getRouterFunction());
    }
}
