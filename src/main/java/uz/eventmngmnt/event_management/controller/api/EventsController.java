package uz.eventmngmnt.event_management.controller.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.eventmngmnt.event_management.entity.Events;
import uz.eventmngmnt.event_management.service.Impl.EventsService;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventsController {
    private final EventsService eventsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return eventsService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return eventsService.getById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Events event) {
        return eventsService.save(event);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Events event) {
        return eventsService.update(id, event);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return eventsService.delete(id);
    }
}
