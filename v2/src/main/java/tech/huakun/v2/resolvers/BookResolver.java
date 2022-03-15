package tech.huakun.v2.resolvers;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
import tech.huakun.v2.entity.Author;
import tech.huakun.v2.entity.Book;

public @Component
class BookResolver implements GraphQLResolver<Book> {
    /**
     * Given a Book object (the book should contain a name attribute but no author attribute)
     * Find the author who wrote the book and return an Author object
     * @param book
     * @return book's author
     */
    public Author author(Book book) {
        return new Author(book.getName() + "'s author");
    }
}
