package ru.trofimov.warehouse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.model.User;
import ru.trofimov.warehouse.security.Token;

import javax.servlet.http.HttpServletRequest;

public interface UserRestController {

    @Operation(summary = "Get all users with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Info.class))}),
            @ApiResponse(responseCode = "403", description = "You are not an admin :)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Did not find any users",
                    content = @Content)})
    ResponseEntity<Info<User>> getAllUsers(HttpServletRequest request, long offset, long limit);

    @Operation(summary = "Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication was successful and a token was issued",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Info.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid login or password",
                    content = @Content)})
    ResponseEntity<Token> login(User user);

    @Operation(summary = "Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registration was successful and a token was issued",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Info.class))}),
            @ApiResponse(responseCode = "401", description = "User with this login already exists",
                    content = @Content)})
    ResponseEntity<Token> signup(User user);
}
