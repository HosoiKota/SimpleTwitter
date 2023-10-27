package com.example.xJava8.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("users")
@Data
public class User {
    @Id
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String description;
    @Column("created_date")
    private Date createdDate;
    @Column("updated_date")
    private Date updatedDate;
}
