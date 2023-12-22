package uz.eventmngmnt.event_management.service;

import org.springframework.http.ResponseEntity;

public abstract class Service<T> {
    public abstract ResponseEntity<?> getAll();
    public abstract ResponseEntity<?> getById(Long id);
    public abstract ResponseEntity<?> save(T t);
    public abstract ResponseEntity<?> update(Long id, T t);
    public abstract ResponseEntity<?> delete(Long id);
}
