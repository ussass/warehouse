package ru.trofimov.warehouse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.trofimov.warehouse.model.User;
import ru.trofimov.warehouse.security.jwtOld.JwtUser;
import ru.trofimov.warehouse.service.UserService;

@Service("userDetailServiceIml")
public class UserDetailServiceIml implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailServiceIml(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }

        return JwtUser.fromUser(user);
    }
}

