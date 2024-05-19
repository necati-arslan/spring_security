package spring_security.spring_security.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring_security.spring_security.DTO.CreateUserRequest;
import spring_security.spring_security.Repository.UserRepository;
import spring_security.spring_security.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User checkUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }

    public User createUser(CreateUserRequest request){

        if(checkUserByUsername(request.username())!=null)
        {
            throw new IllegalArgumentException("Username already exists: " + request.username());
        }
        User newUser = new User(request.username(),passwordEncoder.encode(request.password()),request.roles());
        return userRepository.save(newUser);
    }
}
