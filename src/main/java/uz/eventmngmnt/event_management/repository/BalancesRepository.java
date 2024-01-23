package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Balance;

import java.util.Optional;

public interface BalancesRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUserId(Long userId);
}
