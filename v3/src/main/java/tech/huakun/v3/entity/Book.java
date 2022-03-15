package tech.huakun.v3.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Book {
    private final String name;   // with @RequiredArgsConstructor and final, I can avoid writing a constructor for name
    private Author author;
}
