package io.examples.r2dbc;

import io.examples.r2dbc.handler.BlogHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;

@SpringBootApplication
public class R2dbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(
            BlogHandler blogHandler) {
        return nest(path("/v1/blogs"), blogHandler.getRouterFunction());
    }
}
