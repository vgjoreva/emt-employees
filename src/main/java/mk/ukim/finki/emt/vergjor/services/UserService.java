package mk.ukim.finki.emt.vergjor.services;

import mk.ukim.finki.emt.vergjor.models.User;

import java.util.List;

public interface UserService {

    void registerUser(User user);
    User findUserById(String user_id);
    List<User> findByDepartmentID(int department_id);
    String existsByEmail(String email);
    boolean activateUserAccount(int code);
    boolean isUserRegistered(String user_id);
    void sendNewPassword(String email);
    void updateUserPassword(String id, String password);

}
