package uz.eventmngmnt.event_management.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.eventmngmnt.event_management.entity.Transactions;
import uz.eventmngmnt.event_management.repository.TransactionsRepository;
import uz.eventmngmnt.event_management.service.Service;

import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class TransactionsServiceImpl extends Service<Transactions> {

    private final TransactionsRepository transactionsRepository;
    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(transactionsRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        Transactions transactions = transactionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " Transactions not found"));
        return ResponseEntity.ok(transactions);
    }

    @Override
    public ResponseEntity<?> save(Transactions transactions) {
        if (transactions.getId() != null)
            throw new IllegalArgumentException("Unable to POST, Id should be null");

        return ResponseEntity.ok(transactionsRepository.save(transactions).getId());
    }

    @Override
    public ResponseEntity<?> update(Long id, Transactions transactions) {
        if (transactions.getId() == null)
            throw new IllegalArgumentException("Unable to PUT, Id shouldn't be null");

        if (!isExist(transactions.getId()))
            throw new NoSuchElementException(transactions.getId() + " transactions not found");

        return ResponseEntity.ok(transactionsRepository.save(transactions).getId());
    }

    private boolean isExist(Long id) {
        return transactionsRepository.existsById(id);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id is null");

        transactionsRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
