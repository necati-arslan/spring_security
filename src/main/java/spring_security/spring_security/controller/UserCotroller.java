package spring_security.spring_security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring_security.spring_security.DTO.AuthRequest;
import spring_security.spring_security.DTO.CreateUserRequest;
import spring_security.spring_security.Repository.UserRepository;
import spring_security.spring_security.model.User;
import spring_security.spring_security.security.UserService;
import spring_security.spring_security.service.JwtService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserCotroller {

    private  final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserCotroller(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

        this.userService = userService;
    }

    @PostMapping("login")
    public String login(@RequestBody AuthRequest request){
        try {
            // Creating UsernamePasswordAuthenticationToken object
            // to send it to authentication manager.
            // Attention! We used two parameters constructor.
            // It sets authentication false by doing this.setAuthenticated(false);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
            // we let the manager do its job.
            Authentication authentication =authenticationManager.authenticate(token);
            // if there is no exception thrown from authentication manager,
            // we can generate a JWT token and give it to user.
            if (authentication.isAuthenticated()) {
                String jwt=  jwtService.generateToken(request.username());
                return jwt;
            }else {
                throw new UsernameNotFoundException("Authentication failed for user: " + request.username());
            }
        }catch (AuthenticationException e) {
            // Catch any authentication exception and handle it appropriately
            return "Invalid username or password";
        }



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

        return user;

    }

    @PostMapping("/addNewUser")
    public User addUser(@RequestBody CreateUserRequest request){

        System.out.println("----------------" +request.username());

       return userService.createUser(request);
    }


}
