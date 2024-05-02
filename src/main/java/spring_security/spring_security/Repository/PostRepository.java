package spring_security.spring_security.Repository;

import org.springframework.data.repository.CrudRepository;
import spring_security.spring_security.model.Post;

public interface PostRepository extends CrudRepository<Post,Long> {

}
