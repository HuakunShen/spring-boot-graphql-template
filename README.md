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

## GraphQL Schema Used in modules v1, v2, v3

```gql
type Query {
    greeting: String!
    echo(msg: String!): String!
}

input LoginInput {
    username: String!
    password: String!
}

type LoginResponse {
    success: Boolean!
    msg: String!
}

type Mutation {
    echoMutation(msg: String!): String!
    login(input: LoginInput!): LoginResponse!
}
```



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
}

```

### V3

```java
@Controller
public class RootController {
  /**
   * query {
   * 	greeting
   * }
   * @return "Hello World"
   */
  @QueryMapping
  public String greeting() {
    return "hello world";
  }

  /**
   * query {
   *   echo(msg: "echo")
   * }
   * @param msg
   * @return msg
   */
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
 * Form Data:
 *     msg: message
 * @param msg
 * @return msg
 */
@PostMapping("/echo-post")
public String echo_post(@RequestParam String msg) {
    return msg;
}

/**
 * form-data
 *   username: user
 *   password: password
 */
@PostMapping("/login")
public String login(String username, String password) {
  return username + " logged in with password " + password;
}

/**
 * json request body
 * {
 *     "username": "user",
 *     "password": "password"
 * }
 */
@PostMapping("/login-req-body")
public String login_req_body(@RequestBody Map<String, String> payload) {
    return payload.get("username") + " logged in with password " + payload.get("password");
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
public class MutationResolver implements GraphQLMutationResolver {
  /**
   * mutation {
   * 	echoMutation(msg: "echo mutation")
   * }
   */
  public String echoMutation(String msg) {
    return msg;
  }

  /**
   * mutation Login($input: LoginInput!) {
   *   login(input: $input) {
   *     msg
   *     success
   *   }
   * }
   * Variable:
   * {
   *   "input": {
   *     "username": "huakun",
   *     "password": "password"
   *   }
   * }
   * @param input
   * @return LoginResponse
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
   * mutation Login($input: LoginInput!) {
   *   login(input: $input) {
   *     msg
   *     success
   *   }
   * }
   *
   * Variable:
   * {
   *   "input": {
   *     "username": "huakun",
   *     "password": "password"
   *   }
   * }
   * @param input LoginInput
   * @return LoginResponse
   */
  @MutationMapping
  public LoginResponse login(@Argument("input") LoginInput input) {
      return new LoginResponse(true, input.getUsername() + " logged in successfully");
  }
}
```


## Migrate from Rest to V2

Use `@Component` instead of `@RestController` for the class.

The term resolver in graphql is equivalent to controller in spring boot MVC.

Path variables and request parameters should be replaced by arguments in graphql.


### Get
#### Rest
```java
@RestController
public class RootController {
  @GetMapping("/echo")
  public String echo(@RequestParam String msg) {
    return msg;
  }
}
```

#### v2

Remove all decorators, `@RequestParam`, `@GetMapping`, `@PostMapping`.

Make sure the resolver class implements `GraphQLQueryResolver` interface.

```java
@Component
public class QueryResolver implements GraphQLQueryResolver {
  public String echo(String msg) {
    return msg;
  }
}
```

### Post

For regular post request such as the following

```java
@PostMapping("/echo-post")
public String echo_post(@RequestParam String msg) {
    return msg;
}
```

It's exactly the same as what we did with Get Request/Graphql Query, remove `@PostMapping` and `@RequestParam` (if any). The class for mutation methods should implement `GraphQLMutationResolver` instead of `GraphQLQueryResolver`.

```java
public class MutationResolver implements GraphQLMutationResolver {
  /**
   * mutation {
   * 	echoMutation(msg: "echo mutation")
   * }
   */
  public String echoMutation(String msg) {
    return msg;
  }
}
```

For complex request body payload in json format

#### Rest

```java
/**
 * json request body
 * {
 *     "username": "user",
 *     "password": "password"
 * }
 */
@PostMapping("/login-req-body")
public String login_req_body(@RequestBody Map<String, String> payload) {
    return payload.get("username") + " logged in with password " + payload.get("password");
}
```

#### Mutation

```java
/**
 * mutation Login($input: LoginInput!) {
 *   login(input: $input) {
 *     msg
 *     success
 *   }
 * }
 * Variable:
 * {
 *   "input": {
 *     "username": "huakun",
 *     "password": "password"
 *   }
 * }
 * @param input
 * @return LoginResponse
 */
public LoginResponse login(LoginInput input) {
  return new LoginResponse(true, input.getUsername() + " logged in successfully");
}
```