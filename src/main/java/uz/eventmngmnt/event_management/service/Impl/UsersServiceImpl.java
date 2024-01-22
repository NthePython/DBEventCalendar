package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.UserSignUp;
import uz.eventmngmnt.event_management.entity.Users;
import uz.eventmngmnt.event_management.repository.UsersRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class UsersServiceImpl extends Service<Users> {

    private final UsersRepository repository;
//    private final ValidationService validationService = new ValidationService();

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        Users user = repository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " User not found"));
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> save(Users users) {
        if (users.getId() != null)
            throw new IllegalArgumentException("Unable to POST, Id should be null");

//        validation(users);

        return ResponseEntity.ok(repository.save(users).getId());

    }

    @Override
    public ResponseEntity<?> update(Long id, Users users) {
        if (users.getId() == null)
            throw new IllegalArgumentException("Unable to PUT, Id shouldn't be null");

        if (!isExist(users.getId()))
            throw new NoSuchElementException(users.getId() + " user not found");

//        validation(sessions);

        return ResponseEntity.ok(repository.save(users).getId());

    }

    private boolean isExist(Long id) {
        return repository.existsById(id);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        if (!isExist(id))
            throw new NoSuchElementException(id + " user not found");

        repository.deleteById(id);
        return ResponseEntity.ok(id);
    }

    public ResponseEntity<?> signUp(UserSignUp user) {
        if (user == null)
            throw new IllegalArgumentException("User is null");

        if (!user.getPassword().equals(user.getPassword2()))
            throw new IllegalArgumentException("Password and ConfirmPassword are not equal");

        Users new_user = new Users(null, user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getUsername(), user.getPassword(), null);
        return ResponseEntity.ok(new_user.getId());
    }
}
