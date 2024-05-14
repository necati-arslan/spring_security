package spring_security.spring_security.repository;

import org.springframework.stereotype.Repository;
import spring_security.spring_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String userName);
}
