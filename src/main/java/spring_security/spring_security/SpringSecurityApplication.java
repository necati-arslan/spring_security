package spring_security.spring_security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security.spring_security.Repository.PostRepository;
import spring_security.spring_security.Repository.UserRepository;
import spring_security.spring_security.model.Post;
import spring_security.spring_security.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;



@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PostRepository posts, UserRepository users, PasswordEncoder encoder) {
		return args -> {
			users.save(new User("user", encoder.encode("password"), "ROLE_USER"));
			users.save(new User("admin",encoder.encode("password"),"ROLE_USER,ROLE_ADMIN"));
			posts.save(new Post("Hello, World!","hello-world","Welcome to my new blog!","Dan Vega"));
		};
	}



}

