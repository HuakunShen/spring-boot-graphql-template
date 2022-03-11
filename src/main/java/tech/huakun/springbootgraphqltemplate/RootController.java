package tech.huakun.springbootgraphqltemplate;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RootController {
    @QueryMapping
    public String greeting() {
        return "hello world";
    }
}


