package sit.project.mathrainingapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import sit.project.mathrainingapi.entities.User;
import sit.project.mathrainingapi.entities.UserProfile;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    @Query("{ '_id': ?0 }")
    UserProfile updateUserProfileFields(String id, UserProfile userProfile);
}
