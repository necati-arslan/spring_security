package spring_security.spring_security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security.spring_security.Repository.PostRepository;
import spring_security.spring_security.model.Post;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PostRepository posts) {
		return args -> {

			posts.save(new Post("Hello, World!","hello-world","Welcome to my new blog!","Dan Vega"));
		};
	}

}

