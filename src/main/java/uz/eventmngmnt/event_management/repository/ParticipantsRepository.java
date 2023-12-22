package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Participants;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
}
