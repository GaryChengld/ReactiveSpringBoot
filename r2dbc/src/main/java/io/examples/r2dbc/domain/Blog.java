package io.examples.r2dbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
}
