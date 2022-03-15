package tech.huakun.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "hello world";
    }
}
