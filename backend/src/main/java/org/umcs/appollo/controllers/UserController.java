package org.umcs.appollo.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umcs.appollo.api.UsersApi;
import org.umcs.appollo.configuration.TokenProvider;
import org.umcs.appollo.model.api.User;
import org.umcs.appollo.model.api.UserLoginAttempt;
import org.umcs.appollo.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/")
public class UserController implements UsersApi {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;

    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }




    @Override
    public ResponseEntity<String> loginUser(UserLoginAttempt userLoginAttempt) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginAttempt.getUsername(),
                        userLoginAttempt.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

    @Override
   // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return UsersApi.super.createUser(user);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer id) {
        return UsersApi.super.deleteUserById(id);
    }

    @Override
    public ResponseEntity<User> editUserById(Integer id, User user) {
        return UsersApi.super.editUserById(id, user);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer id) {
        return UsersApi.super.getUserById(id);
    }



}
