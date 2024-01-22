package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Balance;
import uz.eventmngmnt.event_management.entity.Events;
import uz.eventmngmnt.event_management.entity.Participants;
import uz.eventmngmnt.event_management.entity.enums.Roles;
import uz.eventmngmnt.event_management.repository.ParticipantsRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ParticipantsServiceImpl extends Service<Participants> {
    private final UsersServiceImpl userService;
    private final EventsService eventService;
    private final BalanceServiceImpl balanceService;

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

        Participants organizer = participantsRepository.findByIdAndRole(eventId, Roles.ORGANIZER).orElseThrow(()
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
        if (balance.getBalance() < event.getCost())
            throw new IllegalArgumentException("Not enough money");
        else {
            balance.setBalance(balance.getBalance() - event.getCost());
            Long organizerId = (Long) getOrganizerId(participants.getEventId()).getBody();
            Balance organizerBalance = balanceService.getByUserId(organizerId);
            organizerBalance.setBalance(organizerBalance.getBalance() + event.getCost());
            balanceService.update(balance.getId(), balance);
            balanceService.update(organizerBalance.getId(), organizerBalance);
        }

        return ResponseEntity.ok(participantsRepository.save(participants).getId());
    }
}
