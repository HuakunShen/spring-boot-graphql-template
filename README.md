# Spring Boot Graphql Template

A spring boot project template that supports graphql, and graphiql (web interface).

This repo provides sample code for different styles of Graphql Implementation and the corresponding Restful API for the purpose of intuitive comparison.

This repo contains 4 modules

- rest: regular restful API
- v1: not recommended, complicated, not easy to implement `graphql-java` approach see [official tutorial](https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/)
- v2: Similar to Nodejs Apollo conventional implementation
  - Compatible with older spring boot versions (2.3.x, 2.6.x)
  - See [Tutorial](https://www.baeldung.com/spring-graphql)
  - See [Blog in Chinese](https://www.cnblogs.com/dionysun/p/12130440.html), and its [Code](https://github.com/dionylon/springboot-graphql-demo) (the libraries used were out-dated)
  - My code in module [v2](./v2) uses the latest graphql libraries at the time of writing
- v3: Most Recommended (simplest) approach
  - Uses `@Controller`, `@QueryMapping`, `@MutationMapping`, etc. which is similar to rest mvc
  - Drawback: requires spring boot version >=2.7.0.M1 and <3.0.0-M1
  - See [Documentation](https://docs.spring.io/spring-graphql/docs/1.0.0-M5/reference/html/) and [samples](https://github.com/spring-projects/spring-graphql/tree/main/samples)
  - [Official sample I refered to](https://github.com/spring-projects/spring-graphql/tree/main/samples/webmvc-http-security)

[v2](./v2) and [v3](./v3) (most) are the recommended approaches, with useful plugins like `graphiql`, `graphql playground`, `altair`, `voyager`.

Below I map Restful style `get` and `post` requests to different GraphQL Implementations so that you can compare them side by side and choose the style you prefer.

## Get Request With or Without Argument/Param (GraphQL Query)

### Restful

```java
@RestController
public class RootController {
  @GetMapping("/greeting")
  public String greeting() {
    return "Hello World";
  }

  /**
    * http://localhost:8080/echo?msg=hello
    */
  @GetMapping("/echo")
  public String echo(@RequestParam String msg) {
    return msg;
  }

  /**
    * http://localhost:8080/echo/message
    */
  @GetMapping("/echo/{msg}")
  public String echoPathVar(@PathVariable String msg){return msg;}
}
```

### V1

```java
// GraphQLDataFetchers
@Component
public class GraphQLDataFetchers {
  public DataFetcher getGreetingFetcher() {
    return dataFetchingEnvironment -> {
      return "hello world";
    };
  }

  public DataFetcher getEchoFetcher() {
    return dataFetchingEnvironment -> {
      String msg = dataFetchingEnvironment.getArgument("msg");
      return msg;
    };
  }
}

// GraphQLProvider
@Component
public class GraphQLProvider {
  // ...
  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
      .type(newTypeWiring("Query")
            .dataFetcher("greeting", graphQLDataFetchers.getGreetingFetcher()))
      .type(newTypeWiring("Query")
            .dataFetcher("echo", graphQLDataFetchers.getEchoFetcher()));
  }
}
```

### V2

```java
@Component
public class QueryResolver implements GraphQLQueryResolver {
  public String greeting() {
    return "Hello World";
  }

  public String echo(String msg) {
    return msg;
  }
}

```

### V3

```java
@Controller
public class RootController {
  @QueryMapping
  public String greeting() {
    return "hello world";
  }

  @QueryMapping
  public String echo(@Argument String msg) {
    return msg;
  }
}
```

## Post Request (Mutation)

### Rest

```java
/**
 * form-data
 *   username: user
 *   password: password
 */
@PostMapping("/login")
public String login(String username, String password) {
  return username + " logged in with password " + password;
}
```

### V1

```java
// GraphQLDataFetchers
public DataFetcher getEchoMutationFetcher() {
  return dataFetchingEnvironment -> {
    String msg = dataFetchingEnvironment.getArgument("msg");
    return msg;
  };
}
// GraphQLProvider
public class GraphQLProvider {
  // ...
  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
      .type(newTypeWiring("Mutation")
        .dataFetcher("echoMutation", graphQLDataFetchers.getEchoMutationFetcher()));
  }
}
```

### V2

```java
@Component
public class MutationResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
  /**
    * mutation {
    * 	echoMutation(msg: "hello")
    * }
    */
  public String echoMutation(String msg) {
    return msg;
  }

  /**
    * json request body
    * {
    *     "input": {
    *				"username": "user",
    *     		"password": "password"
    *			}
    * }
    */
  public LoginResponse login(LoginInput input) {
    return new LoginResponse(true, input.getUsername() + " logged in successfully");
  }
}
```

### V3

```java
@Controller
public class RootController {
  /**
  * mutation {
  *   echoMutation(msg: "hello")
  * }
  */
  @MutationMapping
  public String echoMutation(@Argument String msg) {
    return msg;
  }

  /**
   * json request body
   * {
   *     "input": {
   *				"username": "user",
   *     		"password": "password"
   *			}
   * }
   */
  @PostMapping("/login-req-body")
  public String login_req_body(@RequestBody Map<String, String> payload) {
    return payload.get("username") + " logged in with password " + payload.get("password");
  }
}
```
