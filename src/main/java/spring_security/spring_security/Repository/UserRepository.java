package spring_security.spring_security.Repository;

import org.springframework.data.repository.CrudRepository;
import spring_security.spring_security.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUsername(String username);

}