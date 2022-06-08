package org.umcs.appollo.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.api.UsersApi;
import org.umcs.appollo.configuration.RoleNames;
import org.umcs.appollo.configuration.TokenProvider;
import org.umcs.appollo.model.RoleEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.AuthToken;
import org.umcs.appollo.model.api.User;
import org.umcs.appollo.model.api.UserLoginAttempt;
import org.umcs.appollo.services.UserService;

import java.util.Set;

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
    public ResponseEntity<AuthToken> loginUser(UserLoginAttempt userLoginAttempt) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginAttempt.getUsername(),
                        userLoginAttempt.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthToken token = new AuthToken();
        token.setValue(jwtTokenUtil.generateToken(authentication));
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        User result ;
        try {
            result = userService.addNewUser(user);
        }
        catch (ResponseStatusException e){
            throw new RuntimeException(e.getMessage());
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(Integer id) {
        try {
            userService.delete(id);
        }
        catch (ResponseStatusException e){
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<User> editUserById(Integer id, User user) {
        UserEntity caller = userService.findOneEntity(
                ((org.springframework.security.core.userdetails.User)SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername());


        boolean clearToEdit = false;
        for (RoleEntity role: caller.getRoles()) {
            if (role.getName().equals(RoleNames.ROLE_ADMIN)) {
                clearToEdit = true;
                break;
            } else if (role.getName().equals(RoleNames.ROLE_USER)) {
                int user_id = userService.findOne(
                                ((org.springframework.security.core.userdetails.User)SecurityContextHolder
                                        .getContext()
                                        .getAuthentication()
                                        .getPrincipal())
                                        .getUsername()).getId();

                if (user_id == id)
                    clearToEdit = true;
            }
        }

        if (!clearToEdit)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to edit this user.");

        User editedUser = userService.edit(id, user);
        return ResponseEntity.ok().body(editedUser);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(Integer id) {
        User user;
        user = userService.findOne(id);
        return ResponseEntity.ok().body(user);
    }



}
