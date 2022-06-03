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
import org.umcs.appollo.model.RoleEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;
import org.umcs.appollo.repository.RoleRepository;
import org.umcs.appollo.repository.UserRepository;

import java.util.*;

@Component
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository,
                           UserConverter userConverter, RoleRepository roleRepository,
                           @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.roleRepository = roleRepository;
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
    public User addNew(User user) {
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user provided.");

        UserEntity userEntity = userConverter.FromApiToEntity(user);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();

        // TODO: Change role section for potential production :)
        RoleEntity roleUser = roleRepository.findByName(RoleNames.ROLE_USER);
        RoleEntity roleAdmin = roleRepository.findByName(RoleNames.ROLE_ADMIN);
        if (roleUser == null) {
            roleUser = new RoleEntity(RoleNames.ROLE_USER, "Regular user role.", new HashSet<>());
            roleRepository.save(roleUser);
        }

        if (roleAdmin == null) {
            roleAdmin = new RoleEntity(RoleNames.ROLE_ADMIN, "Administrator user.", new HashSet<>());
            roleRepository.save(roleAdmin);
        }
        roles.add(roleUser);
        roles.add(roleAdmin);

        userEntity.setRoles(roles);
        userEntity.setAnswers(new ArrayList<>());
        userEntity.setPolls(new ArrayList<>());

        user = userConverter.FromEntityToApi(userEntity);
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
    public User findOne(String username) {
        User out = userConverter.FromEntityToApi(userRepository.findByUsername(username));
        if (out == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user of name " + username + " found.");

        return out;
    }

    @Override
    public User findOne(int id) {
        try {
            return userConverter.FromEntityToApi(userRepository.findById(id).orElseThrow());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user of id " + id + " found.");
        }
    }

    @Override
    public User edit(int id, User data) {
        UserEntity target = userRepository.getById(id);
        target.setUsername(data.getUsername());
        target.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));
        target.setEmail(data.getEmail());

        Set<RoleEntity> roles = new HashSet<>();
        for (String roleName: data.getRole()) {
            RoleEntity roleToAdd = roleRepository.findByName(roleName);
            if (roleToAdd == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No role of name " + roleName + " found.");

            roles.add(roleToAdd);
        }

        target.setRoles(roles);
        return userConverter.FromEntityToApi(target);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
