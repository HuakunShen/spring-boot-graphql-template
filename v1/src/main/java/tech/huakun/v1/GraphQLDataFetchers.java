package tech.huakun.v1;


import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import tech.huakun.v1.entity.LoginInput;
import tech.huakun.v1.entity.LoginResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public DataFetcher getEchoMutationFetcher() {
        return dataFetchingEnvironment -> {
            String msg = dataFetchingEnvironment.getArgument("msg");
            return msg;
        };
    }

    public DataFetcher getLoginFetcher() {
        return dataFetchingEnvironment -> {
//            LoginInput input = dataFetchingEnvironment.getArgument("input");
            return new LoginResponse(true, " logged in successfully");
        };
    }
}