package tech.huakun.v3;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import tech.huakun.v3.entity.LoginInput;
import tech.huakun.v3.entity.LoginResponse;

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
        return "Hello World";
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

    /**
     * mutation {
     *   echoMutation(msg: "echo mutation")
     * }
     * @param msg
     * @return msg
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
