package com.example.xJava8.service;

import com.example.xJava8.entity.User;
import com.example.xJava8.model.LoginUserDetails;
import com.example.xJava8.repository.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private JpaUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> loginUser = repo.findByName(name);
        return loginUser.map(user -> new LoginUserDetails(user))
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }
}
