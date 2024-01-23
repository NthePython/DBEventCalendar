package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Participants;
import uz.eventmngmnt.event_management.entity.enums.Roles;

import java.util.Optional;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    Optional<Participants> findByEventIdAndRole(Long eventId, Roles role);
    Optional<Participants> findByUserId(Long userId);
    Optional<Participants> findByEventId(Long eventId);
}
