package com.example.xJava8.repository;

import com.example.xJava8.entity.Message;
import com.example.xJava8.entity.MessageJoinUser;
import com.example.xJava8.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void signUp(User user) {
        String sql = "insert into users (name, email, password, description) values (:name, :email, :password, :description)";

        jdbc.update(sql, new BeanPropertySqlParameterSource(user));
    }

    public void userUpdate(User user) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE users SET ");
        sql.append("    name = :name, ");
        sql.append("    email = :email, ");
        if(StringUtils.hasText(user.getPassword())) {
            sql.append("    password = :password, ");
        }
        sql.append("    description = :description, ");
        sql.append("    updated_date = CURRENT_TIMESTAMP ");
        sql.append("WHERE id = :id");

        jdbc.update(sql.toString(), new BeanPropertySqlParameterSource(user));
    }

    public List<User> selectByName(String name) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM users ");
        sql.append("WHERE name = :name");

        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        return jdbc.query(sql.toString(), param, new BeanPropertyRowMapper<User>(User.class));
    }

}
