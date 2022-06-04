package org.umcs.appollo.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.api.UsersApi;
import org.umcs.appollo.configuration.TokenProvider;
import org.umcs.appollo.model.api.AuthToken;
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

       return ResponseEntity.ok(result);
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
    public ResponseEntity<User> editUserById(Integer id, User user) {
        User editedUser;
        try {
             editedUser = userService.edit(id, user);
        } catch(ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(editedUser);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer id) {
        User user;
        try{
            user = userService.findOne(id);
        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(user);
    }



}
