package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.trofimov.warehouse.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

}
