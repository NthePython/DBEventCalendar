package uz.eventmngmnt.event_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.eventmngmnt.event_management.entity.enums.Roles;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long eventId;
    private Roles role;

    public Participants() {
    }
}
