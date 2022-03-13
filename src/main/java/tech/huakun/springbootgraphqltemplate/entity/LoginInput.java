package tech.huakun.springbootgraphqltemplate.entity;

import lombok.Data;

@Data
public class LoginInput {
    private String username;
    private String password;
}
