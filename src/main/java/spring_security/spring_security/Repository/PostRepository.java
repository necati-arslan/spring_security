package spring_security.spring_security.Repository;

import  org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import spring_security.spring_security.model.Post;

public interface PostRepository extends MongoRepository<Post, ObjectId> {

}
