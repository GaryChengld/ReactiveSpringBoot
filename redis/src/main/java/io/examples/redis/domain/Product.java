package io.examples.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Gary Cheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String category;
}
