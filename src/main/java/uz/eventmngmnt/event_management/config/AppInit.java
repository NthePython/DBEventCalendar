package uz.eventmngmnt.event_management.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uz.eventmngmnt.event_management.entity.*;
import uz.eventmngmnt.event_management.repository.*;

import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class AppInit implements ApplicationRunner {
    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;
    private final ParticipantsRepository participantsRepository;
    private final BalancesRepository balancesRepository;
    private final TransactionsRepository transactionsRepository;

    private void init() {
        Users user = new Users();
        Events event = new Events();
        Participants participant = new Participants();
        Balance balance = new Balance();
        Transactions transaction = new Transactions();

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

    @Override
    public void run(ApplicationArguments args) {
        if (usersRepository.count() == 0 || eventsRepository.count() == 0 ||
                participantsRepository.count() == 0 || balancesRepository.count() == 0
                || transactionsRepository.count() == 0) {
            init();
        }
    }
}
