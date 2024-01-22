package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Events;
import uz.eventmngmnt.event_management.entity.enums.Roles;

import java.util.Optional;

public interface EventsRepository extends JpaRepository<Events, Long> {
}
