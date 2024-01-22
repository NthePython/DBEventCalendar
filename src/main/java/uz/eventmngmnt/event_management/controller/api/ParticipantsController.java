package uz.eventmngmnt.event_management.controller.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.eventmngmnt.event_management.entity.Participants;
import uz.eventmngmnt.event_management.service.Impl.ParticipantsServiceImpl;

@RestController
@RequestMapping("/api/participants")
@AllArgsConstructor
public class ParticipantsController {
    private final ParticipantsServiceImpl participantsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return participantsService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return participantsService.getById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Participants participant) {
        return participantsService.save(participant);
    }

    @PostMapping("/registerToAnEvent")
    public ResponseEntity<?> registerToAnEvent(@RequestBody Participants participant) {
        try {
            return participantsService.registerToAnEvent(participant);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Participants participant) {
        return participantsService.update(id, participant);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return participantsService.delete(id);
    }
}
