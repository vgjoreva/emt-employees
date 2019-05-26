package mk.ukim.finki.emt.vergjor.web;

import mk.ukim.finki.emt.vergjor.payload.JwtAuthenticationResponse;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import mk.ukim.finki.emt.vergjor.services.CustomUserDetailsService;
import mk.ukim.finki.emt.vergjor.services.UserService;
import mk.ukim.finki.emt.vergjor.services.security.JwtTokenProvider;
import mk.ukim.finki.emt.vergjor.services.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class AuthResource {

    private final CustomUserDetailsService customUserDetailsService;

    private final UserService userServices;
    private final UserRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public AuthResource(CustomUserDetailsService customUserDetailsService, UserService userServices, UserRepository usersRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.userServices = userServices;
        this.usersRepository = usersRepository;
    }

    @RequestMapping("/current_user")
    public UserPrincipal GetCurrentUser(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String id = jwtTokenProvider.getUserIdFromJWT(token);
        UserPrincipal user = (UserPrincipal) customUserDetailsService.loadUserById(id);
        return user;
    }


    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestBody Map<String,String> body){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.get("email"),
                        body.get("password")
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
