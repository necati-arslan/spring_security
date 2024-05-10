package spring_security.spring_security.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import spring_security.spring_security.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
   Optional <User> findByUsername(String username);

    Optional<User> findTopByUsername(String username);
}
