package tech.huakun.v3.controller;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import tech.huakun.v3.entity.Author;
import tech.huakun.v3.entity.Book;

@Controller
public class BookController {
    /**
     * Given a Book object (the book should contain a name attribute but no author attribute)
     * Find the author who wrote the book and return an Author object
     * @param book
     * @return author object who wrote the given book
     */
    @SchemaMapping
    public Author author(Book book) {
        return new Author(book.getName() + "'s author");
    }
}
