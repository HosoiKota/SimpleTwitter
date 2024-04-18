package com.example.xJava8.repository;

import com.example.xJava8.entity.Comment;
import com.example.xJava8.entity.CommentJoinUser;
import com.example.xJava8.entity.Message;
import com.example.xJava8.entity.MessageJoinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<CommentJoinUser> selectAllJoinUser() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("  comments.id as id, ");
        sql.append("  comments.comment as comment, ");
        sql.append("  comments.user_id as userId, ");
        sql.append("  comments.message_id as messageId, ");
        sql.append("  users.name as name, ");
        sql.append("  comments.created_date as createdDate, ");
        sql.append("  comments.updated_date as updatedDate ");
        sql.append("FROM comments ");
        sql.append("INNER JOIN users ");
        sql.append("ON comments.user_id = users.id ");
        sql.append("ORDER BY createdDate ASC ");

        return jdbc.query(sql.toString(), new BeanPropertyRowMapper<CommentJoinUser>(CommentJoinUser.class));
    }

    public Comment selectById(Integer id) {
        String sql = "select * from comments where id = :id;";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);

        return jdbc.queryForObject(sql, param, new BeanPropertyRowMapper<Comment>(Comment.class));
    }

    public void insert(Comment comment) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO comments ( ");
        sql.append("    user_id, ");
        sql.append("    comment, ");
        sql.append("    message_id ");
        sql.append(") VALUES ( ");
        sql.append("    :userId, ");
        sql.append("    :comment, ");
        sql.append("    :messageId ");
        sql.append(")");

        jdbc.update(sql.toString(), new BeanPropertySqlParameterSource(comment));
    }
    public void update(Comment comment) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE comments SET ");
        sql.append("comment = :comment, ");
        sql.append("created_date = CURRENT_TIMESTAMP ");
        sql.append("WHERE id = :id;");

        jdbc.update(sql.toString(), new BeanPropertySqlParameterSource(comment));
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM comments WHERE id = :id;";

        Map<String, Object> param = new HashMap<>();
        param.put("id", id);

        jdbc.update(sql, param);
    }
}
