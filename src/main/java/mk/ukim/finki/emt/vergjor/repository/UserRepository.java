package mk.ukim.finki.emt.vergjor.repository;

import mk.ukim.finki.emt.vergjor.models.Role;
import mk.ukim.finki.emt.vergjor.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.departmentID.department_id = :department_id")
    List<User> findByDepartmentID(@Param("department_id") int department_id);

    @Query("select case when count(u) > 0 then true else false end from User u where u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("select u from User u where u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("select u from User u where u.user_id = :user_id")
    User findByUserID(@Param("user_id") String user_id);

    /*@Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.roleID = :user_role WHERE u.user_id = :user_id")
    int updateUser(@Param("user_role") Role user_role, @Param("user_id") String user_id);
*/
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.user_id = :user_id")
    void updateUserPassword(String user_id, String password);

}
