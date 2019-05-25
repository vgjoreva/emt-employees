package mk.ukim.finki.emt.vergjor.repository;

import mk.ukim.finki.emt.vergjor.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where user_department.department_id = :department_id")
    List<User> findByDepartmentID(@Param("department_id") int department_id);

    @Query("select u from User u where email = :email")
    boolean existsByEmail(@Param("email") String email);

}
