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

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign_up")
    public ModelAndView signUpView(){
        ModelAndView model = new ModelAndView();
        model.addObject("error", "");
        model.addObject("user", new User());
        model.addObject("levels", EmploymentLevel.values());
        model.setViewName("signUpUser");
        return model;
    }

    @PostMapping("/sign_up")
    public String signUp(Model model, @ModelAttribute User user, @RequestParam("repassword") String repassword) {


        model.addAttribute("user", user);
        model.addAttribute("levels", EmploymentLevel.values());

        if (!user.getPassword().equals(repassword)) {
            System.out.println("Passwords don't match!");
            model.addAttribute("error", "Passwords don't match!");
        } else if (userService.existsByEmail(user.getEmail()) == 1){
            System.out.println("User already exists!");
            model.addAttribute("error", "User already exists!");
        }
        else{
            userService.registerUser(user);
            return "redirect:/activation";
        }
        return "signUpUser.html";
    }

    @GetMapping("/activation")
    public String activateUserView(){
        return "activateUser.html";
    }

    @PostMapping("/activation")
    public String activateUser(@RequestParam("code") int code){
        if(userService.activateUserAccount(code))
            return "redirect:/login";
        return "redirect:/activation";
    }

    @GetMapping("/activation/{code}")
    public String activateUserByEmail(@PathVariable("code") int code){
        if(userService.activateUserAccount(code))
            return "redirect:/login";
        return "redirect:/activation";
    }

    @GetMapping("/login")
    public String loginUserView(){
        return "signInUser.html";
    }

    @GetMapping("/forgot_password")
    public String forgotPasswordView(Model model){
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "forgotPassword.html";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(Model model, @RequestParam("email") String email){

        if (userService.existsByEmail(email) == 1) {
            userService.sendNewPassword(email);
            model.addAttribute("success", "An email has been sent!");
        }
        else{
            System.out.println("User already exists!");
            model.addAttribute("error", "This user doesn't exist!");
        }
        return "forgotPassword.html";
    }

    @GetMapping("/new_password/{id}")
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
    }


}
