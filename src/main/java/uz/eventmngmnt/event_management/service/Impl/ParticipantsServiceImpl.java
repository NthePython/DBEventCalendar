package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Participants;
import uz.eventmngmnt.event_management.repository.ParticipantsRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ParticipantsServiceImpl extends Service<Participants> {

    private final ParticipantsRepository participantsRepository;
    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(participantsRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        Participants participants = participantsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " Participants not found"));
        return ResponseEntity.ok(participants);
    }

    @Override
    public ResponseEntity<?> save(Participants participants) {
        return ResponseEntity.ok(participantsRepository.save(participants).getId());
    }

    @Override
    public ResponseEntity<?> update(Long id, Participants participants) {
        if (participants.getUserId() == null || participants.getEventId() == null)
            throw new IllegalArgumentException("Unable to PUT, Id shouldn't be null");

        if (!isExist(participants.getUserId()))
            throw new NoSuchElementException(participants.getUserId() + " participants not found");

        return ResponseEntity.ok(participantsRepository.save(participants).getUserId());
    }

    private boolean isExist(Long id) {
        return participantsRepository.existsById(id);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        participantsRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
