package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Participants;
import uz.eventmngmnt.event_management.entity.enums.Roles;

import java.util.List;
import java.util.Optional;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    Optional<Participants> findByEventIdAndRole(Long eventId, Roles role);
    List<Participants> findByUserId(Long userId);
    List<Participants> findByEventId(Long eventId);
}
