package com.example.xJava8.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {
    @NotNull
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")
    @Size(max = 20)
    private String name;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$",
        message = "メールアドレスのフォーマットが正しくありません。")
    @Size(max = 50)
    private String email;
    @NotNull
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")
    @Size(max = 20)
    private String password;
    @Size(max = 100)
    private String description;
}
