package com.example.xJava8.service;

import com.example.xJava8.entity.Message;
import com.example.xJava8.entity.MessageJoinUser;
import com.example.xJava8.form.MessageForm;
import com.example.xJava8.repository.jdbc.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> selectAll() {
        return messageRepository.selectAll();
    }

    public MessageForm selectById(Integer id) {
        MessageForm form = entityToForm(messageRepository.selectById(id));
        return form;
    }

    public List<MessageJoinUser> selectAllJoinUser(String start, String end) {
        if (StringUtils.hasText(start)) {
            start = start + " 00:00:00";
        } else {
            start = "2000/01/01 00:00:00";
        }

        if (StringUtils.hasText(end)) {
            end = end + " 23:59:59";
        } else {
            end = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        }

        return messageRepository.selectAllJoinUser(start, end);
    }

    public void doTweet(MessageForm messageForm) {
        Message message = formToEntity(messageForm);
        messageRepository.doTweet(message);
    }

    public void editTweet(MessageForm messageForm) {
        Message message = formToEntity(messageForm);
        messageRepository.editTweet(message);
    }

    public void delete(Integer id) {
        messageRepository.delete(id);
    }

    private Message formToEntity(MessageForm messageForm) {
        Message message = new Message();
        message.setId(messageForm.getId());
        message.setMessage(messageForm.getMessage());
        message.setUserId(messageForm.getUserId());
        return message;
    }

    private MessageForm entityToForm(Message message) {
//        if (message == null) {
//            return null;
//        }
        MessageForm form = new MessageForm();
        form.setId(message.getId());
        form.setMessage(message.getMessage());
        form.setUserId(message.getUserId());
        return form;
    }

}
