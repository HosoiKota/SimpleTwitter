package com.example.xJava8.repository.jpa;

import com.example.xJava8.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByName(String name);
}
