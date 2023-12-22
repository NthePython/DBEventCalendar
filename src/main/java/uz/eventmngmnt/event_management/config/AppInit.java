package uz.eventmngmnt.event_management.config;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uz.eventmngmnt.event_management.entity.*;
import uz.eventmngmnt.event_management.repository.*;

import java.sql.Time;
import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class AppInit implements ApplicationRunner {
    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;
    private final ParticipantsRepository participantsRepository;
    private final BalancesRepository balancesRepository;
    private final TransactionsRepository transactionsRepository;

    @Override
    public void run(ApplicationArguments args) {
        Users user = new Users();
        Events event = new Events();
        Participants participant = new Participants();
        Balance balance = new Balance();
        Transactions transaction = new Transactions();

        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("example@example.com");
        user.setPassword("123456");
        user.setPhoneNumber("+998901234567");
        usersRepository.save(user);

        event.setName("Event");
        event.setDescription("Description");
        event.setStartTime(Timestamp.valueOf("2021-10-10 15:00:00"));
        event.setEndTime(Timestamp.valueOf("2021-10-10 17:00:00"));
        event.setCost(10000.00);
        event.setAllDay(false);
        eventsRepository.save(event);

        participant.setUserId(user.getId());
        participant.setEventId(event.getId());
        participantsRepository.save(participant);

        balance.setUserId(user.getId());
        balance.setBalance(10000.00);
        balancesRepository.save(balance);

        transaction.setUserId(user.getId());
        transaction.setEventId(event.getId());
        transaction.setSum(10000.00);
        transaction.setBalanceId(balance.getId());
        transactionsRepository.save(transaction);
    }
}
