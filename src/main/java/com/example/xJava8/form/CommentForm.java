package com.example.xJava8.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CommentForm {

    private Integer id;

    @NotNull(message="コメントを入力してください")
    @Pattern(regexp = "[^(\\s|　)]+", message="コメントを入力してください")
    @Size(max = 140, message="コメントは140文字以内で入力してください")
    private String text;

    private String userId;
    private String messageId;
}
