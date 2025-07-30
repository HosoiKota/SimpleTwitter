package com.example.xJava8.service;

import com.example.xJava8.entity.User;
import com.example.xJava8.form.UserForm;
import com.example.xJava8.repository.jdbc.UserRepository;
import com.example.xJava8.repository.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    public void signUp(UserForm userForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userForm.setPassword(encoder.encode(userForm.getPassword()));
        User user = formToEntity(userForm);
        userRepository.signUp(user);
    }

    public void userUpdate(UserForm userForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (StringUtils.hasText(userForm.getPassword())) {
            userForm.setPassword(encoder.encode(userForm.getPassword()));
        }
        User user = formToEntity(userForm);
        userRepository.userUpdate(user);
    }

    public List<User> selectByName(String name) {
        return userRepository.selectByName(name);
    }

    public Optional<User> findByName(String name) {
        return jpaUserRepository.findByName(name);
    }

    public void updateNgCount(Integer id) {
        userRepository.updateNgCount(id);
    }

    public void resetNgCount(Integer id) {
        userRepository.resetNgCount(id);
    }

    private User formToEntity(UserForm userForm) {
        User user = new User();
        user.setId(userForm.getId());
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setDescription(userForm.getDescription());
        return user;
    }

}
