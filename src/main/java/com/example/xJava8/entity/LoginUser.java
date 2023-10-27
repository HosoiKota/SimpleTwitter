package com.example.xJava8.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;


/**
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class LoginUser {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String description;
    private Date createdDate;
    private Date updatedDate;
}
