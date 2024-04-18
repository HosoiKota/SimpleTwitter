package com.example.xJava8.service;

import com.example.xJava8.entity.User;
import com.example.xJava8.form.SignUpForm;
import com.example.xJava8.form.UserForm;
import com.example.xJava8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void signUp(SignUpForm signUpForm) {
        User user = formToEntity(signUpForm);
        userRepository.signUp(user);
    }

    public void userUpdate(UserForm userForm) {
        User user = formToEntity(userForm);
        userRepository.userUpdate(user);

    }

    public List<User> selectByName(String name) {
        return userRepository.selectByName(name);
    }

    private User formToEntity(SignUpForm signUpForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(signUpForm.getName());
        user.setEmail(signUpForm.getEmail());
        user.setPassword(encoder.encode(signUpForm.getPassword()));
        user.setDescription(signUpForm.getDescription());
        return user;
    }

    private User formToEntity(UserForm userForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(userForm.getId());
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        if (StringUtils.hasText(userForm.getPassword())) {
            user.setPassword(encoder.encode(userForm.getPassword()));
        }
        user.setDescription(userForm.getDescription());
        return user;
    }

}
