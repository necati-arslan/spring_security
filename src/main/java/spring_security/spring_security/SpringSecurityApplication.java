package spring_security.spring_security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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





}

