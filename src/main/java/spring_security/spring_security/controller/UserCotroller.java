package spring_security.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserCotroller {
    @GetMapping("/admin")
    public String admin(){
        return "Admine özel mesajdır";
    }

    @GetMapping("/user")
    public String  dashboard() {
        return "Üyelere özel mesajdır";
    }

    @GetMapping("/home")

    public String index(){
        return "Merhaba. Üye Olun";
    }
}
