package com.example.xJava8.service;

import com.example.xJava8.entity.Comment;
import com.example.xJava8.entity.CommentJoinUser;
import com.example.xJava8.form.CommentForm;
import com.example.xJava8.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    public List<CommentJoinUser> selectAllJoinUser() {
        return commentRepository.selectAllJoinUser();
    }

    public CommentForm selectById(Integer id) {
        CommentForm form = entityToForm(commentRepository.selectById(id));
        return form;
    }
    public void insert(CommentForm commentForm) {
        Comment comment = formToEntity(commentForm);
        commentRepository.insert(comment);
    }
    public void update(CommentForm commentForm) {
        Comment comment = formToEntity(commentForm);
        commentRepository.update(comment);
    }
    public void delete(Integer id) {
        commentRepository.delete(id);
    }
    private Comment formToEntity(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setComment(commentForm.getComment());
        comment.setUserId(commentForm.getUserId());
        comment.setMessageId(commentForm.getMessageId());
        return comment;
    }

    private CommentForm entityToForm(Comment comment) {
        CommentForm form = new CommentForm();
        form.setId(comment.getId());
        form.setComment(comment.getComment());
        form.setUserId(comment.getUserId());
        form.setMessageId(comment.getMessageId());
        return form;
    }

}
