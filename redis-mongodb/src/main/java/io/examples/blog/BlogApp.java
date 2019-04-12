package io.examples.blog;

import io.examples.blog.handler.BlogHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
public class BlogApp {

    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(
            BlogHandler blogHandler) {
        return nest(path("/v1/blogs"), blogHandler.getRouterFunction());
    }
}
