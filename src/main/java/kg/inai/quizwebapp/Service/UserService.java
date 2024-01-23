package kg.inai.quizwebapp.Service;

import kg.inai.quizwebapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username);

    //public void createUserMap(UserDTO userDTO);
    public User findByUserName(String name);

}
