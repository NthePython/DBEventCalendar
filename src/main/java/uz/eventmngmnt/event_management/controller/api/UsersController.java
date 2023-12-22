package uz.eventmngmnt.event_management.controller.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.eventmngmnt.event_management.entity.Users;
import uz.eventmngmnt.event_management.service.Impl.UsersServiceImpl;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {
    private final UsersServiceImpl usersService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return usersService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return usersService.getById(id);
    }

    @GetMapping("/save")
    public ResponseEntity<?> save(@RequestBody Users user) {
        return usersService.save(user);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Users user) {
        return usersService.update(id, user);
    }
}
