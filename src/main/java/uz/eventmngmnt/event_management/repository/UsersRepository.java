package uz.eventmngmnt.event_management.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.eventmngmnt.event_management.entity.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Transactional
    @Modifying
    @Query("update Users u set u.firstName = ?2, u.lastName = ?3, u.phoneNumber = ?4, u.email = ?5, u.username = ?6, u.password = ?7 where u.id = ?1")
    void update(Long id, String firstName, String lastName, String phoneNumber, String email, String username, String password);

    @Transactional
    @Modifying
    @Query("update Users u set u.firstName = ?2 where u.id = ?1")
    void updateFirstName(Long id, String firstName);

    @Transactional
    @Modifying
    @Query("update Users u set u.lastName = ?2 where u.id = ?1")
    void updateLastName(Long id, String lastName);

    @Transactional
    @Modifying
    @Query("update Users u set u.phoneNumber = ?2 where u.id = ?1")
    void updatePhoneNumber(Long id, String phoneNumber);

    @Transactional
    @Modifying
    @Query("update Users u set u.email = ?2 where u.id = ?1")
    void updateEmail(Long id, String email);

    @Transactional
    @Modifying
    @Query("update Users u set u.username = ?2 where u.id = ?1")
    void updateUsername(Long id, String username);

    @Transactional
    @Modifying
    @Query("update Users u set u.password = ?2 where u.id = ?1")
    void updatePassword(Long id, String password);
}
