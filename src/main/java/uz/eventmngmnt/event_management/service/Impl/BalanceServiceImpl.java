package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Balance;
import uz.eventmngmnt.event_management.repository.BalancesRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class BalanceServiceImpl extends Service<Balance> {

    private final BalancesRepository balanceRepository;

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(balanceRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        Balance balance = balanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " Balance not found"));
        return ResponseEntity.ok(balance);
    }

//    public ResponseEntity<?> getByUserId(Long id) {
//        if (id == null)
//            throw new IllegalArgumentException("Id is null");
//
//        Balance balance = balanceRepository.findBy().orElseThrow(() -> new NoSuchElementException(id + " Balance not found"));
//        return ResponseEntity.ok(balance);
//    }

    @Override
    public ResponseEntity<?> save(Balance balance) {
        if (balance.getId() != null)
            throw new IllegalArgumentException("Unable to POST, Id should be null");

        return ResponseEntity.ok(balanceRepository.save(balance).getId());
    }

    @Override
    public ResponseEntity<?> update(Long id, Balance balance) {
        if (balance.getId() == null)
            throw new IllegalArgumentException("Unable to PUT, Id shouldn't be null");

        if (!isExist(balance.getUserId()))
            throw new NoSuchElementException(balance.getId() + " balance not found");

        return ResponseEntity.ok(balanceRepository.save(balance).getId());
    }

    private boolean isExist(Long id) {
        return balanceRepository.existsById(id);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        balanceRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
