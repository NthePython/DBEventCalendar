package uz.eventmngmnt.event_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.eventmngmnt.event_management.entity.enums.Roles;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "eventId"})
})
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long eventId;
    private Roles role;

    public Participants() {
    }
}
