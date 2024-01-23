package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsernameAndPassword(String username, String password);
}
