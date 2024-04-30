package spring_security.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_security.spring_security.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}