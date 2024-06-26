package com.example.xJava8.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

// TODO: UserFormとしているがユーザー編集画面で使用しているため修正必要？
@Data
public class UserForm {
    private Integer id;
    @NotNull(message="ユーザー名を入力してください")
    @Pattern(regexp = "[^(\\s|　)]+", message="ユーザー名を入力してください")
    @Size(max = 20, message="ユーザー名は20文字以内で入力してください")
    private String name;
    @NotNull(message="メールアドレスを入力してください")
    @Pattern(regexp = "[^(\\s|　)]+", message="メールアドレスを入力してください")
    @Size(max = 50, message="メールアドレスは50文字以内で入力してください")
    private String email;
    private String password;
    private String description;
}
