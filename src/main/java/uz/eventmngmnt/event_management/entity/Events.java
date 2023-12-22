package uz.eventmngmnt.event_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.eventmngmnt.event_management.entity.enums.EventTypes;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String place;
    private Timestamp startTime;
    private Timestamp endTime;
    private Double cost;
    private Boolean allDay;

    private EventTypes eventType;

    public Events() {
    }
}
