package mk.ukim.finki.emt.vergjor.services;

import mk.ukim.finki.emt.vergjor.models.User;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import mk.ukim.finki.emt.vergjor.services.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        return UserPrincipal.create(user);
    }
    @Transactional
    public UserDetails loadUserById(String id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                Exception::new
        );

        return UserPrincipal.create(user);
    }
}
