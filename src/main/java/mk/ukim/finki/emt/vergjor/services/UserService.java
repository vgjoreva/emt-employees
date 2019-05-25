package mk.ukim.finki.emt.vergjor.services;

import mk.ukim.finki.emt.vergjor.models.User;

import java.util.List;

public interface UserService {

    void registerUser(User user);
    User findUserById(String user_id);
    List<User> findByDepartmentID(int department_id);
    boolean existsByEmail(String email);

}
