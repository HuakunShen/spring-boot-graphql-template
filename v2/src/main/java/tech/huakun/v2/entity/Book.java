package tech.huakun.v2.entity;

import lombok.Data;

@Data
public class Book {
    private final String name;   // with @RequiredArgsConstructor and final, I can avoid writing a constructor for name
    private Author author;
}
