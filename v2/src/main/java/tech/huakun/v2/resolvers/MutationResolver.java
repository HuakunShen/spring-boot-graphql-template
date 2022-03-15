package tech.huakun.v2.resolvers;

//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import tech.huakun.v2.entity.LoginInput;
import tech.huakun.v2.entity.LoginResponse;

@Component
public class MutationResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    /**
     * mutation {
     *   echoMutation(msg: "hello")
     * }
     * @param msg
     * @return msg
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
     *
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
