package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Balance;
import uz.eventmngmnt.event_management.entity.UserSignUp;
import uz.eventmngmnt.event_management.entity.UserWithBalance;
import uz.eventmngmnt.event_management.entity.Users;
import uz.eventmngmnt.event_management.repository.UsersRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class UsersServiceImpl extends Service<Users> {

    private final UsersRepository repository;
    private final BalanceServiceImpl balanceService;
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
        Double balance = balanceService.getByUserId(id).getBalance();
        UserWithBalance response = new UserWithBalance();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setBalance(balance);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> save(Users users) {
        if (users.getId() != null)
            throw new IllegalArgumentException("Unable to POST, Id should be null");

//        validation(users);

        Long userId = repository.save(users).getId();
        balanceService.save(new Balance(null, userId, 0.0));
        return ResponseEntity.ok(userId);

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
        new_user = repository.save(new_user);
        Balance balance = new Balance(null, new_user.getId(), 0.0);
        balanceService.save(balance);
        return ResponseEntity.ok(new_user.getId());
    }

    public ResponseEntity<?> signIn(String username, String password) {
        if (username == null || password == null)
            throw new IllegalArgumentException("Username or password is null");

        try{
            Users user = repository.findByUsernameAndPassword(username, password).orElseThrow(() -> new NoSuchElementException(username + " User not found"));
            return ResponseEntity.ok(user.getId());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> topUpBalance(Long userId, Double amount) {
        if (userId == null || amount == null)
            throw new IllegalArgumentException("Id or amount is null");

        repository.findById(userId).orElseThrow(() -> new NoSuchElementException(userId + " User not found"));
        Balance balance = balanceService.getByUserId(userId);
        balance.setBalance(balance.getBalance() + amount);
        balanceService.update(userId, balance);
        return ResponseEntity.ok("Balance updated");
    }
}
