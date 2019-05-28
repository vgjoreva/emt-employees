package mk.ukim.finki.emt.vergjor.web;

import mk.ukim.finki.emt.vergjor.models.EmploymentLevel;
import mk.ukim.finki.emt.vergjor.models.User;
import mk.ukim.finki.emt.vergjor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/sign_up")
    public void signUp(@RequestBody Map<String,String> body) {

        User user = new User();
        user.setFull_name(body.get("full_name"));
        user.setEmail(body.get("email"));
        user.setPassword(body.get("password"));
        for(EmploymentLevel e : EmploymentLevel.values()){
            if(e.ordinal() == Integer.parseInt(body.get("level"))){
                user.setLevel(e);
                break;
            }
        }
        userService.registerUser(user);
    }

    @PostMapping("/activation")
    public void activateUser(@RequestParam("code") int code){
        userService.activateUserAccount(code);
    }

    @GetMapping("/activation/validation")
    public String isActivationCodeValid(@RequestParam("code") int code){
        return userService.isActivationCodeValid(code);
    }

    @GetMapping("/user/exists")
    public String existsUserByEmail(@RequestParam("email") String email){
        return userService.existsByEmail(email);
    }

    @PostMapping("/login/forgot_password")
    public void forgotPassword(@RequestParam("email") String email){
        userService.sendNewPassword(email);
    }

    @PatchMapping("/edit_user")
    public void editUserInfo(@RequestBody Map<String,String> body){

        User user = userService.findUserById(body.get("id"));

        if(!user.getFull_name().equals(body.get("full_name")))
            user.setFull_name(body.get("full_name"));

        if(!user.getEmail().equals(body.get("email")))
            user.setEmail(body.get("email"));

        if(body.get("level") != null)
            for(EmploymentLevel e : EmploymentLevel.values()){
                if(e.ordinal() == Integer.parseInt(body.get("level"))){
                    user.setLevel(e);
                    break;
                }
            }

        userService.editUserInfo(user);
    }

    @PatchMapping("/new_password")
    public void newPassword(@RequestBody Map<String,String> body){
        userService.changePassword(body.get("id"), body.get("password"));
    }

    @GetMapping("/account/validation")
    public String isAccountValid(@RequestParam("id") String id){
        return userService.isAccountValid(id);
    }

}
