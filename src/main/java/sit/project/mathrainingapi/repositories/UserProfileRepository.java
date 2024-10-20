package sit.project.mathrainingapi.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import sit.project.mathrainingapi.entities.UserProfile;

import java.util.List;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile,String> {
    @Query(value = "{}")
    List<UserProfile> findUserProfilesPageableAndSort(String sortBy, Pageable pageable);

    @Query(value = "{}", count = true)
    long countUserProfiles();
}
