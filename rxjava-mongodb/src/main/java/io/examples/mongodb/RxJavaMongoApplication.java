package io.examples.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
public class RxJavaMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RxJavaMongoApplication.class, args);
    }

}
