package mk.ukim.finki.emt.vergjor.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid", strategy = "uuid2")
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private EmploymentLevel level;

    @ManyToOne
    private Department departmentID;

    @ManyToOne
    private Role roleID;

    public User(){}

    public User(String full_name, String email, String password, EmploymentLevel level, Department departmentID, Role roleID) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.departmentID = departmentID;
        this.roleID = roleID;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmploymentLevel getLevel() {
        return level;
    }

    public void setLevel(EmploymentLevel level) {
        this.level = level;
    }

    public Department getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Department departmentID) {
        this.departmentID = departmentID;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }
}
