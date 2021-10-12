package ru.trofimov.warehouse.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.warehouse.exception.InvalidRequestBodyException;
import ru.trofimov.warehouse.model.User;
import ru.trofimov.warehouse.repositories.UserRepository;
import ru.trofimov.warehouse.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRolesToStringRoles();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        user.addRolesFromString();
        logger.info("saved new user with id={}", user.getId());
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        logger.info("Got all users");
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        users.forEach(User::addRolesFromString);
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        logger.info("Got user by login = {}", login);
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.addRolesFromString();
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        logger.info("Got user by id = {}", id);
        User user = userRepository.findById(id).get();
        user.addRolesFromString();
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("deleted user with id = {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new InvalidRequestBodyException("invalid login or password");
    }
}
