package mk.ukim.finki.emt.vergjor.web;

import mk.ukim.finki.emt.vergjor.repository.AccountActivationsRepository;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import mk.ukim.finki.emt.vergjor.services.CustomUserDetailsService;
import mk.ukim.finki.emt.vergjor.services.UserService;
import mk.ukim.finki.emt.vergjor.services.security.JwtTokenProvider;
import mk.ukim.finki.emt.vergjor.services.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@Controller
public class AuthResource {

    private final CustomUserDetailsService customUserDetailsService;

    private final UserService userServices;
    private final UserRepository usersRepository;
    private final AccountActivationsRepository activationsRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEndoder;

    public AuthResource(CustomUserDetailsService customUserDetailsService, UserService userServices, UserRepository usersRepository, AccountActivationsRepository activationsRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.userServices = userServices;
        this.usersRepository = usersRepository;
        this.activationsRepository = activationsRepository;
    }

    @GetMapping("/current_user")
    public String GetCurrentUser(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String id = jwtTokenProvider.getUserIdFromJWT(token);
        UserPrincipal user = (UserPrincipal) customUserDetailsService.loadUserById(id);
        if(activationsRepository.isUserRegistered(user.getId())){
            return "redirect:/login";
        }
        return "redirect:/activation";
    }


    @PostMapping("/login/validate")
    public String LoginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password){

        if(usersRepository.existsByEmail(email) == 1 && passwordEndoder.matches(password, usersRepository.findByEmail(email).getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwtTokenProvider.generateToken(authentication);
            return "redirect:/current_user";
        }
        return "redirect:/login";
    }
}
