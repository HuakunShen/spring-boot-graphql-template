package tech.huakun.rest;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RootController {
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

    /**
     * http://localhost:8080/echo?msg=hello
     * @param msg
     * @return
     */
    @GetMapping("/echo")
    public String echo(@RequestParam String msg) {
        return msg;
    }

    /**
     * http://localhost:8080/echo/message
     * @param msg
     * @return
     */
    @GetMapping("/echo/{msg}")
    public String echoPathVar(@PathVariable String msg){return msg;}

    /**
     * Form Data:
     *     msg: hello
     * @param msg
     * @return
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
     * @param payload
     * @return
     */
    @PostMapping("/login-req-body")
    public String login_req_body(@RequestBody Map<String, String> payload) {
        return payload.get("username") + " logged in with password " + payload.get("password");
    }
}
