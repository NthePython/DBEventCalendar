package uz.eventmngmnt.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eventmngmnt.event_management.entity.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
