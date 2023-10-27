package com.example.xJava8.entity;


import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("comments")
@Data
public class Comment {

    private Integer id;
    private String text;
    private String userId;
    private String messageId;
    private Date createdDate;
    private Date updatedDate;
}
