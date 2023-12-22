package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Balance;

public interface BalancesRepository extends JpaRepository<Balance, Long> {
}
