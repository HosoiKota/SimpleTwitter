package com.example.xJava8.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommentJoinUser {
    private Integer id;
    private String text;
    private String userId;
    private String messageId;
    private Date createdDate;
    private Date updatedDate;

    private String name;
}
