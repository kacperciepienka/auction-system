package pl.auction_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.auction_system.model.AccType;
import pl.auction_system.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // findById jest w standardzie
    // Optional: to są klucze naturalne
    Optional<User> findUserByUsernameEqualsIgnoreCase(String username);
    Optional<User> findUserByEmailEqualsIgnoreCase(String email);

    Page<User> findAllByAccType(AccType accType, Pageable pageable);

    //by first and last name
        Page<User> findAllByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
        Page<User> findAllByLastNameContainingIgnoreCase(String firstName, Pageable pageable);
}
