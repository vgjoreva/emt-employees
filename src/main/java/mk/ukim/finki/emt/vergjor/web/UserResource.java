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
@Controller
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
    public boolean activateUser(@RequestParam("code") int code){
        return userService.activateUserAccount(code);
    }

    @GetMapping("/activation/{code}")
    public boolean activateUserByEmail(@PathVariable("code") int code){
        return userService.activateUserAccount(code);
    }

    @GetMapping("/user/exists/{email}")
    public String existsUserByEmail(@PathVariable("email") String email){
        return userService.existsByEmail(email);
    }

    @PostMapping("/forgot_password")
    public int forgotPassword(@RequestParam("email") String email){

        String doesUserExist = userService.existsByEmail(email);
        if (doesUserExist != "Valid") {
            userService.sendNewPassword(email);
            return 1;
        }
        else return 0;
    }

    /*@GetMapping("/new_password/{id}")
    public String newPasswordView(HttpServletResponse response, Model model, @PathVariable("id") String id){
        model.addAttribute("user", id);
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);
        return "newPassword.html";
    }

    @PostMapping("/new_password")
    public String newPassword(HttpServletRequest request, @RequestParam("password") String password){
        Cookie c[] = request.getCookies();
        userService.updateUserPassword(c[0].getValue(), password);
        return "redirect:/login";
    }*/


}
