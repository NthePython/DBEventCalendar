package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Events;
import uz.eventmngmnt.event_management.repository.EventsRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class EventsService extends Service<Events> {

    private final EventsRepository eventsRepository;
    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(eventsRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        Events events = eventsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " Event not found"));
        return ResponseEntity.ok(events);
    }

    @Override
    public ResponseEntity<?> save(Events events) {
        if (events.getId() != null)
            throw new IllegalArgumentException("Unable to POST, Id should be null");

//        validation(events);

        return ResponseEntity.ok(eventsRepository.save(events).getId());
    }

    @Override
    public ResponseEntity<?> update(Long id, Events events) {
        if (events.getId() == null)
            throw new IllegalArgumentException("Unable to PUT, Id shouldn't be null");

        if (!isExist(events.getId()))
            throw new NoSuchElementException(events.getId() + " event not found");

//        validation(events);

        return ResponseEntity.ok(eventsRepository.save(events).getId());
    }

    private boolean isExist(Long id) {
        return eventsRepository.existsById(id);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        if (!isExist(id))
            throw new NoSuchElementException(id + " event not found");

        eventsRepository.deleteById(id);
        return ResponseEntity.ok(id);
    }
}
