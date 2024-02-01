package project.jocl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.jocl.entity.ERole;
import project.jocl.entity.Role;
import project.jocl.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'PRESIDENT'")
    List<User> findUsersByRoleUser();
}
