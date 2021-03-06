package org.umcs.appollo.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.configuration.RoleNames;
import org.umcs.appollo.converters.UserConverter;
import org.umcs.appollo.exceptions.ConflictException;
import org.umcs.appollo.exceptions.ResourceNotFoundException;
import org.umcs.appollo.model.RoleEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;
import org.umcs.appollo.repository.UserRepository;

import java.util.*;

@Component
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository,
                           UserConverter userConverter,
                           @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Invalid username or password.");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(roleEntity -> {
           authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()));
        });
        return authorities;
    }

    @Override
    public User addNewUser(User user) {
        UserEntity userEntity = userConverter.FromApiToEntity(user);

        UserEntity a = userRepository.findByUsername(user.getUsername());
        if (a != null)
            throw new ConflictException("Username is already taken.");
        a = userRepository.findByEmail(user.getEmail());
        if (a != null)
            throw new ConflictException("Email is already taken.");

        userEntity.setId(null);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleNames.ROLE_USER));

        userEntity.setRoles(roles);
        userEntity.setAnswers(new ArrayList<>());
        userEntity.setPolls(new ArrayList<>());

        user = userConverter.FromEntityToApi(userRepository.save(userEntity));
        return user;
    }

    public List<User> findAll() {
        List<User> out = new ArrayList<>();
        userRepository.findAll()
                .iterator()
                .forEachRemaining(a -> out.add(userConverter.FromEntityToApi(a)));
        return out;
    }

    @Override
    public User findOne(int id) {
        return userConverter.FromEntityToApi(findOneEntity(id));
    }

    @Override
    public User findOne(String username) {
        return userConverter.FromEntityToApi(findOneEntity(username));
    }

    @Override
    public UserEntity findOneEntity(int id) {
        try {
            return userRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException( "No user of id " + id + " found.",e.getCause());
        }
    }

    @Override
    public UserEntity findOneEntity(String username) {
        UserEntity target = userRepository.findByUsername(username);
        if (target == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user of username " + username + " found.");

        return target;
    }

    @Override
    public User edit(int id, User data) {
        UserEntity target;
        try {
           target  = userRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("No user of id " + id + " found.", e.getCause());
        }

        UserEntity a = userRepository.findByUsername(data.getUsername());
        if (a != null)
            throw new ConflictException("Username already taken.");
        a = userRepository.findByEmail(data.getEmail());
        if (a != null)
            throw new ConflictException("Email already taken.");

        target.setUsername(data.getUsername());
        target.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));
        target.setEmail(data.getEmail());
        target.setFirstName(data.getFirstname());
        target.setLastName(data.getLastname());

        return userConverter.FromEntityToApi(userRepository.save(target));
    }

    @Override
    public void delete(int id) {
        UserEntity user = userRepository.getById(id);
        if(!userRepository.existsById(id))
            throw new ResourceNotFoundException("No user of id " + id + " found.");
        else
            userRepository.delete(user);


    }
}
