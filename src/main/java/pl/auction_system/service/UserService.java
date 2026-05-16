package pl.auction_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.auction_system.exception.*;
import pl.auction_system.model.AccType;
import pl.auction_system.model.User;
import pl.auction_system.repository.UserRepository;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    // POST
    public User addUser(User userToAdd) {
        userRepository.findUserByUsernameEqualsIgnoreCase(userToAdd.getUsername())
                .ifPresent(user -> {
                    throw new UserUsernameAlreadyExistException(userToAdd.getUsername());
                });

        userRepository.findUserByEmailEqualsIgnoreCase(userToAdd.getEmail())
                .ifPresent(user -> {
                    throw new UserEmailAlreadyExistException(userToAdd.getEmail());
                });

        userToAdd.setAccType(AccType.USER);
        Random random = new Random();
        int randomNum = random.nextInt(10000, 99999);
        String userPrefix = userToAdd.getUsername().substring(0,2).toUpperCase();
        int year = LocalDate.now().getYear();
        int count = userRepository.userCount();
        int day = LocalDate.now().getDayOfMonth();

        userToAdd.setUserNumber("USR-" + year + userPrefix + count + day + randomNum);

        return userRepository.save(userToAdd);
    }

    public User addAdmin(User userToAdd) {
        userRepository.findUserByUsernameEqualsIgnoreCase(userToAdd.getUsername())
                .ifPresent(user -> {
                    throw new UserUsernameAlreadyExistException(userToAdd.getUsername());
                });

        userRepository.findUserByEmailEqualsIgnoreCase(userToAdd.getEmail())
                .ifPresent(user -> {
                    throw new UserEmailAlreadyExistException(userToAdd.getEmail());
                });

        userToAdd.setAccType(AccType.ADMIN);
        return userRepository.save(userToAdd);
    }

    // DELETE
    public void deleteUserByUsername(String username) {
        User user = userRepository.findUserByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundByUsernameException(username));

        userRepository.delete(user);
    }

    // PUT
    public User changeUsername(String oldUsername, String newUsername){
        User user = userRepository.findUserByUsernameEqualsIgnoreCase(oldUsername)
                .orElseThrow(() -> new UserNotFoundByUsernameException(oldUsername));

        if (oldUsername.equals(newUsername)){
            return user;
        }

        userRepository.findUserByUsernameEqualsIgnoreCase(newUsername)
                .ifPresent(u -> {
                    throw new UserUsernameAlreadyExistException(newUsername);
                });

        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    public User changeEmail(String oldEmail, String newEmail){
        User user = userRepository.findUserByEmailEqualsIgnoreCase(oldEmail)
                .orElseThrow(() -> new UserNotFoundByEmailException(oldEmail));

        if (oldEmail.equals(newEmail)) {
            return user;
        }

        userRepository.findUserByEmailEqualsIgnoreCase(newEmail)
                .ifPresent(u -> {
                    throw new UserEmailAlreadyExistException(newEmail);
                });

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    public User changeAccTypeByUsername(String username, AccType accType){
        User user = userRepository.findUserByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundByUsernameException(username));

        if (accType.equals(user.getAccType())){
            return user;
        }

        user.setAccType(accType);
        return userRepository.save(user);
    }

    // GET
    public User findUserByUsernameEqualsIgnoreCase(String username) {
        return userRepository.findUserByUsernameEqualsIgnoreCase(username).
                orElseThrow(() -> new UserNotFoundByUsernameException(username));
    }

    public User findUserByEmailEqualsIgnoreCase(String email) {
        return userRepository.findUserByEmailEqualsIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundByEmailException(email));
    }

    public User findUserByUserNumberEqualsIgnoreCase(String userNumber){
        return userRepository.findUserByUserNumberEqualsIgnoreCase(userNumber)
            .orElseThrow(() -> new UserNotFoundByUserNumberException(userNumber));
    }

    public Page<User> findAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Page<User> findAllByAccType(AccType accType, Pageable pageable) {
        return userRepository.findAllByAccType(accType, pageable);
    }

    //by first and last name
    public Page<User> findAllByFirstNameEqualsIgnoreCase(String firstName, Pageable pageable) {
        return userRepository.findAllByFirstNameEqualsIgnoreCase(firstName, pageable)
;    }

    public Page<User> findAllByLastNameEqualsIgnoreCase(String lastName, Pageable pageable) {
        return userRepository.findAllByLastNameEqualsIgnoreCase(lastName, pageable);
    }
}

