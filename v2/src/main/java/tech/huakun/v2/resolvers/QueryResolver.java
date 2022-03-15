package tech.huakun.v2.resolvers;

//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import tech.huakun.v2.entity.Book;


@Component
public class QueryResolver implements GraphQLQueryResolver {
    /**
     * query {
     *     greeting
     * }
     */
    public String greeting() {
        return "Hello World";
    }

    /**
     * query {
     *   echo (msg: "Hello World")
     * }
     */
    public String echo(String msg) {
        return msg;
    }

    public Book getBook(String name) {
        return new Book(name);
    }
}

