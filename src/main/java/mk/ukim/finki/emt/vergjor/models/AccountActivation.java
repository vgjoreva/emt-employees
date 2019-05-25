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

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @OneToOne
    private User userID;

    public AccountActivation(){}

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
}
