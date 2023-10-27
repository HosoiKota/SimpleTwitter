package com.example.xJava8.repository;

import com.example.xJava8.entity.LoginUser;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginUserRepository {

    private static final String SQL = "SELECT * FROM users WHERE name = :name";

    // ここの実装大分怪しい
    private static final ResultSetExtractor<LoginUser> LOGIN_USER_EXTRACTOR
            = (rs) -> {
        if (rs.next()) {
            return new LoginUser(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("description"),
                    rs.getDate("created_date"),
                    rs.getDate("updated_date"));
        }
        return null;
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LoginUser> findByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        LoginUser loginUser = jdbcTemplate.query(SQL, params, LOGIN_USER_EXTRACTOR);
        return Optional.ofNullable(loginUser);
    }
}
