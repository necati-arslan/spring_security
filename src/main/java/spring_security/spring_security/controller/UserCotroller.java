package spring_security.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring_security.spring_security.Repository.UserRepository;
import spring_security.spring_security.model.User;

import java.util.List;
import java.util.Optional;

@RestController

public class UserCotroller {

    private  final UserRepository userRepository;

    public UserCotroller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @GetMapping("/all")
    public List<User> getAllUser(){
       List<User> user= userRepository.findAll();
        System.out.println(user);
       return user;

    }

   @GetMapping("/user/{username}")
    public Optional<User> getUserByName(@PathVariable String username){
        Optional<User> user= userRepository.findTopByUsername(username);
        System.out.println(user);
        return user;

    }
}
