package uz.eventmngmnt.event_management.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignIn {
    private String username;
    private String password;

    public UserSignIn() {
    }
}
