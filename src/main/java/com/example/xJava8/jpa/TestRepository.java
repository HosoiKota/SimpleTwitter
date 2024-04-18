package com.example.xJava8.jpa;

import com.example.xJava8.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Message, Integer> {
    Page<Message> findAll(Pageable pageable);
}
