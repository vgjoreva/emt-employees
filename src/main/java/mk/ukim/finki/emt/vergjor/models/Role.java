package mk.ukim.finki.emt.vergjor.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id")
    private int role_id;

    @Column(name = "role", unique = true)
    private String role;

    public Role(){}

    public Role(int role_id, String role){
        this.role = role;
        this.role_id = role_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
