package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Balance;
import uz.eventmngmnt.event_management.entity.Events;
import uz.eventmngmnt.event_management.entity.Participants;
import uz.eventmngmnt.event_management.entity.Transactions;
import uz.eventmngmnt.event_management.entity.enums.Roles;
import uz.eventmngmnt.event_management.entity.enums.TransactionStatus;
import uz.eventmngmnt.event_management.entity.enums.TransactionType;
import uz.eventmngmnt.event_management.repository.ParticipantsRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ParticipantsServiceImpl extends Service<Participants> {
    private final UsersServiceImpl userService;
    private final EventsService eventService;
    private final BalanceServiceImpl balanceService;
    private final TransactionsServiceImpl transactionsService;

    private final ParticipantsRepository participantsRepository;
    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(participantsRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        Participants participants = participantsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " Participants not found"));
        return ResponseEntity.ok(participants);
    }

    public ResponseEntity<?> getByUserId(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        return ResponseEntity.ok(participantsRepository.findByUserId(id));
    }

    public ResponseEntity<?> getByEventId(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        return ResponseEntity.ok(participantsRepository.findByEventId(id));
    }

    @Override
    public ResponseEntity<?> save(Participants participants) {
        return ResponseEntity.ok(participantsRepository.save(participants).getId());
    }

    @Override
    public ResponseEntity<?> update(Long id, Participants participants) {
        if (participants.getUserId() == null || participants.getEventId() == null)
            throw new IllegalArgumentException("Unable to PUT, Id shouldn't be null");

        if (!isExist(participants.getUserId()))
            throw new NoSuchElementException(participants.getUserId() + " participants not found");

        return ResponseEntity.ok(participantsRepository.save(participants).getUserId());
    }

    private boolean isExist(Long id) {
        return participantsRepository.existsById(id);
    }

    public ResponseEntity<?> getOrganizerId(Long eventId) {
        if (eventId == null)
            throw new IllegalArgumentException("Id is null");

        Participants organizer = participantsRepository.findByEventIdAndRole(eventId, Roles.ORGANIZER).orElseThrow(()
                -> new NoSuchElementException(eventId + " Event not found"));
        return ResponseEntity.ok(organizer.getUserId());
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        participantsRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<?> registerToAnEvent(Participants participants) {
        if (participants.getUserId() == null || participants.getEventId() == null)
            throw new IllegalArgumentException("Unable to register, Id shouldn't be null");
        if (userService.getById(participants.getUserId()) == null)
            throw new NoSuchElementException(participants.getUserId() + " user not found");

        Events event = (Events) eventService.getById(participants.getEventId()).getBody();
        if (event == null)
            throw new NoSuchElementException(participants.getEventId() + " event not found");

        Balance balance = balanceService.getByUserId(participants.getUserId());

        // Initializing credit transaction
        Transactions credit = new Transactions();
        credit.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        credit.setBalanceId(balance.getId());
        credit.setEventId(event.getId());
        credit.setSum(event.getCost());
        credit.setType(TransactionType.CREDIT);
        credit.setStatus(TransactionStatus.PENDING);
        transactionsService.save(credit);

        // Failing transaction if user doesn't have enough money
        if (balance.getBalance() < event.getCost()) {
            credit.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
            credit.setStatus(TransactionStatus.FAILED);
            transactionsService.update(credit.getId(), credit);
            throw new IllegalArgumentException("Not enough money");
        }

        // Preparing participant's balance for update
        balance.setBalance(balance.getBalance() - event.getCost());

        // Getting organizer's details
        Long organizerId = (Long) getOrganizerId(event.getId()).getBody();
        Balance organizerBalance = balanceService.getByUserId(organizerId);

        // Initializing debit transaction
        Transactions debit = new Transactions();
        debit.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        debit.setBalanceId(balance.getId());
        debit.setEventId(event.getId());
        debit.setSum(event.getCost());
        debit.setType(TransactionType.DEBIT);
        debit.setStatus(TransactionStatus.PENDING);
        transactionsService.save(debit);

        // Preparing organizer's balance for update
        organizerBalance.setBalance(organizerBalance.getBalance() + event.getCost());

        // Updating balances and completing credit transaction
        balanceService.update(balance.getId(), balance);
        credit.setStatus(TransactionStatus.SUCCESSFUL);
        credit.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        transactionsService.update(credit.getId(), credit);

        // Updating balances and completing debit transaction
        balanceService.update(organizerBalance.getId(), organizerBalance);
        debit.setStatus(TransactionStatus.SUCCESSFUL);
        debit.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        transactionsService.update(debit.getId(), debit);


        return ResponseEntity.ok(participantsRepository.save(participants).getId());
    }
}
