package com.project.demo.service;

import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//wlasna implementacja serwisu do zarzadzania uzytkownikami w naszym systemie
// -> póki co potrafi tylko zwrócić odpowiedni obiekt
@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //uzywane przez Spring Security do sprawdzenia czy dany użytkownik istnieje
    //-> jeśli tak to oczekuje zwrócenia obiektu  UserDetails na podstawie Principal
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Nie znaleziono użytkowika o loginie " + userName);
        }
        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(),
                user.getEnabled(), true, true, true,
                AuthorityUtils.createAuthorityList(user.getRole()));
    }

    @Override
    public User createNewUser(User user) {
        if (Objects.isNull(userRepository.findByUserName(user.getUserName()))) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (Objects.isNull(user.getEnabled())) {
                user.setEnabled(true);
            }
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }


}
