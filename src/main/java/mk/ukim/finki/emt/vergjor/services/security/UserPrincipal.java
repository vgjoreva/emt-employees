package mk.ukim.finki.emt.vergjor.services.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mk.ukim.finki.emt.vergjor.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;


public class UserPrincipal implements UserDetails {

    private String id;

    private String fullName;

    private String email;

    @JsonIgnore
    private String password;

    private GrantedAuthority authorities;

    public UserPrincipal(String id, String name, String email, String password, GrantedAuthority authorities) {
        this.id = id;
        this.fullName = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {

        GrantedAuthority authorities =
                new SimpleGrantedAuthority("ROLE_" + user.getRoleID());

        return new UserPrincipal(
                user.getUser_id(),
                user.getFull_name(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
