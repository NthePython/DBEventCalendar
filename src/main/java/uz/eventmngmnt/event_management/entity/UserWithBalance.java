package uz.eventmngmnt.event_management.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserWithBalance {
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String username;

    private Timestamp joinedDate;
    private Double balance;
}
