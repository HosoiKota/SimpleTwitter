package com.example.xJava8.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {
    @NotNull(message="ユーザー名を入力してください")
    @Pattern(regexp = "[^(\\s|　)]+", message="ユーザー名を入力してください")
    @Size(max = 20, message="コメントは20文字以内で入力してください")
    private String name;
    @NotNull(message="メールアドレスを入力してください")
    @Pattern(regexp = "[^(\\s|　)]+", message="メールアドレスを入力してください")
    @Size(max = 50, message="メールアドレスは50文字以内で入力してください")
    private String email;
    private String password;
    private String description;
}
