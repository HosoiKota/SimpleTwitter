package com.example.xJava8.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;

@Data
public class Message {
    private Integer id;
    private String userId;
    private String text;
    private Date createdDate;
    private Date updatedDate;
}
