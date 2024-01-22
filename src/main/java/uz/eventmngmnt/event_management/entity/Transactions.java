package uz.eventmngmnt.event_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.eventmngmnt.event_management.entity.enums.TransactionStatus;
import uz.eventmngmnt.event_management.entity.enums.TransactionType;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long eventId;
    @Column(nullable = false)
    private Timestamp startTime;
    private Timestamp endTime;
    private Double sum;
    private TransactionStatus status;
    private TransactionType type;
    private Long balanceId;

    public Transactions() {

    }
}
