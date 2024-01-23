package kg.inai.quizwebapp.repository;

import kg.inai.quizwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository for user entity.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Method to find user by username. May later move to service class for better abstraction/organization.
    User findByUsername(String username);
    User findByUserID(Long userID);

}
