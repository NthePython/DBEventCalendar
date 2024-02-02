package uz.eventmngmnt.event_management.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUp {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String password2;

    public UserSignUp() {
    }

    public UserSignUp(String firstName, String lastName, String phoneNumber, String email, String username, String password, String password2) {
        if (password.equals(password2)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.username = username;
            this.password = password;
        }
        else {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}
