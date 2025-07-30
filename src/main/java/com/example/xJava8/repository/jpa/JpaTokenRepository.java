package com.example.xJava8.repository.jpa;

import com.example.xJava8.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JpaTokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t INNER JOIN FETCH t.user WHERE t.user.name = :name AND token = :token AND expiration_date >= CURRENT_TIMESTAMP")
    public List<Token> findByNameAndToken(String name, String token);

    @Transactional
    public void deleteByUserId(Integer userId);
}
