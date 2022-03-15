package tech.huakun.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "hello world";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        return username + " logged in with password " + password;
    }

    @PostMapping("/login-req-body")
    public String login_req_body(@RequestBody Map<String, String> payload) {
        return payload.get("username") + " logged in with password " + payload.get("password");
    }
}
