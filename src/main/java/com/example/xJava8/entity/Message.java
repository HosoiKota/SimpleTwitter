package com.example.xJava8.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    private Integer id;
    private String userId;
    private String message;
    private Date createdDate;
    private Date updatedDate;
}
