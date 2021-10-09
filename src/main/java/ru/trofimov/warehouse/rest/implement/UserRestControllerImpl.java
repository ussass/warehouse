package ru.trofimov.warehouse.rest.implement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.exception.InvalidRequestBodyException;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.model.Role;
import ru.trofimov.warehouse.model.User;
import ru.trofimov.warehouse.rest.UserRestController;
import ru.trofimov.warehouse.security.Token;
import ru.trofimov.warehouse.security.jwt.JwtProvider;
import ru.trofimov.warehouse.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerImpl implements UserRestController {

    private final long DEFAULT_LIMIT = 50;

    private final UserService userService;

    private final JwtProvider jwtProvider;

    public UserRestControllerImpl(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    public ResponseEntity<Info<User>> getAllUsers(HttpServletRequest request,
                                                         @RequestParam(defaultValue = "0") long offset,
                                                         @RequestParam(defaultValue = "" + DEFAULT_LIMIT) long limit) {
        List<User> users = userService.findAll();
        long fullSize = users.size();
        users = users.stream().skip(offset).limit(limit).collect(Collectors.toList());

        Info<User> info = new Info<>(offset, limit, users, fullSize, request.getRequestURL().toString());

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        User authUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        Token token = new Token(jwtProvider.generateToken(authUser.getLogin()));
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping("signup")
    public ResponseEntity<Token> signup(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        User newUser = userService.findByLogin(user.getLogin());
        if (newUser != null){
            throw new InvalidRequestBodyException("User with this login already exists");
        }
        user.addRole(Role.USER);
        user = userService.save(user);

        Token token = new Token(jwtProvider.generateToken(user.getLogin()));
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.CREATED);
    }
}
