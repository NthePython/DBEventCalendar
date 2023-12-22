package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Events;

public interface EventsRepository extends JpaRepository<Events, Long> {
}
