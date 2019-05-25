package mk.ukim.finki.emt.vergjor.web;

import mk.ukim.finki.emt.vergjor.models.Department;
import mk.ukim.finki.emt.vergjor.models.EmploymentLevel;
import mk.ukim.finki.emt.vergjor.models.User;
import mk.ukim.finki.emt.vergjor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        model.addObject("user", new User());
        model.addObject("levels", EmploymentLevel.values());
        model.setViewName("signUpUser");
        return model;
    }

    @PostMapping("/sign_up")
    public String signUp(@ModelAttribute User user){
        /*user.setUser_id(UUID.randomUUID().toString());*/

        userService.registerUser(user);
        return "redirect:/activation";
    }

    @GetMapping("/activation")
    public String activateUserView(){
        return "activateUser.html";
    }

    @PostMapping("/activation")
    public String activateUser(@RequestParam("code") int code){

        return "redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String activateUserByEmail(@PathVariable("code") int code){

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginUserView(){
        return "signInUser.html";
    }

}
