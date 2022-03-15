package tech.huakun.v2.resolvers;

//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;


@Component
public class QueryResolver implements GraphQLQueryResolver {

    public String greeting() {
        return "Hello World";
    }

    public String echo(String msg) {
        return msg;
    }
}

