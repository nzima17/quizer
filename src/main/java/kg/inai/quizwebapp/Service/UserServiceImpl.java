package kg.inai.quizwebapp.Service;

import kg.inai.quizwebapp.config.UserDetailHandler;
import kg.inai.quizwebapp.dto.UserDTO;
import kg.inai.quizwebapp.model.Role;
import kg.inai.quizwebapp.model.User;
import kg.inai.quizwebapp.repository.RoleRepository;
import kg.inai.quizwebapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

//User service implementation
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder encryption;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(userName);

        if (user == null)
        {
            System.out.println("Invalid username or password!");

            throw new UsernameNotFoundException("Invalid username and/or password!");

        }

        return new UserDetailHandler(user, roleService.getRolesByUser(user.getUserID()));

    }

    //Map roles to authorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

    }

    //Map models
    @Transactional
    public void createUserMap(UserDTO userDTO) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = modelMapper.map(userDTO, User.class);

        //Encode password
        user.setPassword(encryption.encode(user.getPassword()));

        //Assign role
        Role role = roleRepository.findByRoleName("ROLE_USER");

        //Make sure role exists
        if (role == null) {
            role = checkRole();
        }
        user.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_USER")));
        userRepository.save(user);

    }

        private Role checkRole(){

            Role role = new Role();
            role.setRoleName("ROLE_USER");
            return roleRepository.save(role);
    }

    public User findByUserName(String username)
    {

        return userRepository.findByUsername(username);

    }


}
