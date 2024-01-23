package kg.inai.quizwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kg.inai.quizwebapp.model.Role;

import java.util.List;

//Repository for role entity.
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query(value = "select * from _roles where roleid= (select roleid from user_role where userid = :id)", nativeQuery = true)
    public List<Role> findRoleByUser(@Param("id") long id);

}
