package mk.ukim.finki.emt.vergjor.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_activation")
public class AccountActivation {

    @Id
    @Column(name = "activation_code")
    private int activation_code;

    @Column(name = "is_activated")
    private boolean userIsActivated;

    @Column(name = "is_valid")
    private boolean codeIsValid;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @ManyToOne
    private Role employee_position;

    @OneToOne
    private User userID;

    public AccountActivation(){}

    public AccountActivation(int activation_code, boolean userIsActivated, boolean codeIsValid, LocalDateTime registeredAt, LocalDateTime validUntil, Role employee_position, User userID) {
        this.activation_code = activation_code;
        this.userIsActivated = userIsActivated;
        this.codeIsValid = codeIsValid;
        this.registeredAt = registeredAt;
        this.validUntil = validUntil;
        this.employee_position = employee_position;
        this.userID = userID;
    }

    public int getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(int activation_code) {
        this.activation_code = activation_code;
    }

    public boolean isUserIsActivated() {
        return userIsActivated;
    }

    public void setUserIsActivated(boolean userIsActivated) {
        this.userIsActivated = userIsActivated;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public User getUser_id() {
        return userID;
    }

    public void setUser_id(User user_id) {
        this.userID = user_id;
    }

    public Role getEmployee_position() {
        return employee_position;
    }

    public void setEmployee_position(Role employee_position) {
        this.employee_position = employee_position;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public boolean isCodeIsValid() {
        return codeIsValid;
    }

    public void setCodeIsValid(boolean codeIsValid) {
        this.codeIsValid = codeIsValid;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
