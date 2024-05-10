package spring_security.spring_security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring_security.spring_security.Repository.PostRepository;
import spring_security.spring_security.Repository.UserRepository;
import spring_security.spring_security.model.Post;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring_security.spring_security.model.User;


@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PostRepository postsRepositpry, UserRepository userRepository) {
		return args -> {
			postsRepositpry.save(new Post("title demo","Slug demo","content demo","author demo"));
			userRepository.save(new User("demouser1","demo password", "ROLE_USER"));
		};
	}



}

