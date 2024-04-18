package com.example.xJava8.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MessageJoinUser {

    private Integer id;
    private String userId;
    private String message;
    private Date createdDate;
    private Date updatedDate;

    private String name;
}
