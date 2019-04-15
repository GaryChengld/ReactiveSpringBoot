package io.examples.blog.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Blog")
public class Blog {
    @Id
    private String id;
    @TextIndexed
    private String title;
    @TextIndexed
    private String content;
    @Indexed
    private String author;
    @CreatedDate
    private LocalDateTime createdDate;
}
