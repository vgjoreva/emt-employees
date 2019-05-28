package mk.ukim.finki.emt.vergjor.services.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mk.ukim.finki.emt.vergjor.models.Department;
import mk.ukim.finki.emt.vergjor.models.EmploymentLevel;
import mk.ukim.finki.emt.vergjor.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class UserPrincipal implements UserDetails {

    private String id;

    private String fullName;

    private String email;

    private String role;

    private int department_id;

    private String department_name;

    private int level;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id,
                         String name,
                         String email,
                         String role,
                         int department_id,
                         String department_name,
                         int level,
                         String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.fullName = name;
        this.email = email;
        this.role = role;
        this.department_name = department_name;
        this.department_id = department_id;
        this.level = level;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleID().getRole()));

        return new UserPrincipal(
                user.getUser_id(),
                user.getFull_name(),
                user.getEmail(),
                user.getRoleID().getRole(),
                user.getDepartmentID().getId(),
                user.getDepartmentID().getDepartment_name(),
                user.getLevel().ordinal(),
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

    public String getRole(){ return role; }

    public int getDepartment_id() {
        return department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public int getLevel() {
        return level;
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
        return authorities;
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
