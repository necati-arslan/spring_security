package spring_security.spring_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring_security.spring_security.DTO.AuthRequest;
import spring_security.spring_security.Repository.UserRepository;
import spring_security.spring_security.model.User;
import spring_security.spring_security.service.JwtService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserCotroller {

    private  final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserCotroller(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public String login(@RequestBody AuthRequest request){
        // Creating UsernamePasswordAuthenticationToken object
        // to send it to authentication manager.
        // Attention! We used two parameters constructor.
        // It sets authentication false by doing this.setAuthenticated(false);
        System.out.println(request.username());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        // we let the manager do its job.
        Authentication authentication =authenticationManager.authenticate(token);
        // if there is no exception thrown from authentication manager,
        // we can generate a JWT token and give it to user.
        if (authentication.isAuthenticated()) {
            String jwt=  jwtService.generateToken(request.username());
            return jwt;
        }
        throw new UsernameNotFoundException("invalid username {} " + request.username());

    }


    @GetMapping("/admin")
    public String admin(){
        return "Admine özel mesajdır";
    }

    @GetMapping("/user")
    public String  dashboard() {
        return "Üyelere özel mesajdır";
    }

    @PostMapping("/home")

    public String index(){
        System.out.println("test");

        return "Merhaba. Üye Olun";
    }

    @GetMapping("/all")
    public List<User> getAllUser(){
       List<User> user= userRepository.findAll();
       return user;

    }

   @GetMapping("/user/{username}")
    public Optional<User> getUserByName(@PathVariable String username){
        Optional<User> user= userRepository.findTopByUsername(username);
        System.out.println(user);
        return user;

    }


}
