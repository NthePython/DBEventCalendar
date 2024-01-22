package uz.eventmngmnt.event_management.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.eventmngmnt.event_management.entity.Balance;

import java.util.Optional;

public interface BalancesRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUserId(Long userId);
}
