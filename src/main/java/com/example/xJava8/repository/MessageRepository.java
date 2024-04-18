package com.example.xJava8.repository;

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
public class MessageRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<Message> selectAll() {
        String sql = "select * from messages;";

        return jdbc.query(sql, new BeanPropertyRowMapper<Message>(Message.class));
    }

    public Message selectById(Integer id) {
        String sql = "select * from messages where id = :id;";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);

//        List<Message> messages = jdbc.query(sql, param, new BeanPropertyRowMapper<Message>(Message.class));
//        if (messages.isEmpty()) {
//            return null;
//        } else {
//            return messages.get(0);
//        }
        return jdbc.queryForObject(sql, param, new BeanPropertyRowMapper<Message>(Message.class));
    }

    public List<MessageJoinUser> selectAllJoinUser(String start, String end) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    messages.id as id, ");
        sql.append("    messages.message as message, ");
        sql.append("    messages.user_id as userId, ");
        sql.append("    users.name as name, ");
        sql.append("    messages.created_date as createdDate, ");
        sql.append("    messages.updated_date as updatedDate ");
        sql.append("FROM messages ");
        sql.append("INNER JOIN users ");
        sql.append("ON messages.user_id = users.id ");
        sql.append("WHERE messages.created_date BETWEEN :start AND :end ");
        sql.append("ORDER BY createdDate DESC ");

        Map<String, Object> param = new HashMap<>();
        param.put("start", start);
        param.put("end", end);

        return jdbc.query(sql.toString(), param, new BeanPropertyRowMapper<MessageJoinUser>(MessageJoinUser.class));
    }

    public void doTweet(Message message) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO messages ( ");
        sql.append("    user_id, ");
        sql.append("    message ");
        sql.append(") VALUES ( ");
        sql.append("    :userId, ");
        sql.append("    :message ");
        sql.append(")");

        jdbc.update(sql.toString(), new BeanPropertySqlParameterSource(message));
    }

    public void editTweet(Message message) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE messages SET ");
        sql.append("message = :message, ");
        sql.append("created_date = CURRENT_TIMESTAMP ");
        sql.append("WHERE id = :id;");

        jdbc.update(sql.toString(), new BeanPropertySqlParameterSource(message));
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM messages WHERE id = :id;";

        Map<String, Object> param = new HashMap<>();
        param.put("id", id);

        jdbc.update(sql, param);
    }

}
