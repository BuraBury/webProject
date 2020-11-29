package com.project.demo.service;

import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

//wlasna implementacja serwisu do zarzadzania uzytkownikami w naszym systemie
// -> póki co potrafi tylko zwrócić odpowiedni obiekt
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
