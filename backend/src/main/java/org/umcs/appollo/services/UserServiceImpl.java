package org.umcs.appollo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.umcs.appollo.converters.UserConverter;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;
import org.umcs.appollo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private  RoleService roleService;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
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
        UserEntity userEntity = userConverter.FromApiToEntity(user);
        // TODO: 02.06.2022 Dodanie do repozytorium nowego uzytkownika
        user = userConverter.FromEntityToApi(userEntity);
        return user;


    }

    public List<User> findAll() {
        List<UserEntity> listEntity = new ArrayList<>();
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(listEntity::add);
        // TODO: 02.06.2022 Przekonwertowanie listy na api 
        return list;
    }

    @Override
    public User findOne(String username) {
        return userConverter.FromEntityToApi(userRepository.findByUsername(username));
    }

    @Override
    public User edit(String username) {
        // TODO: 02.06.2022 update uzytkownika 
        return null;
    }

    @Override
    public void delete(String username) {
        // TODO: 02.06.2022 usuwanie uzytkownika 

    }


}
