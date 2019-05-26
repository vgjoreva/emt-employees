package mk.ukim.finki.emt.vergjor.repository;

import mk.ukim.finki.emt.vergjor.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where r.role_id = :role_id")
    Role findByRoleID(@Param("role_id") int role_id);

}
