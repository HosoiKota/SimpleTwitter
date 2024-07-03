package com.example.xJava8.form;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
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
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")
    @Size(max = 50)
    private String email;

    @NotNull
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")
    private String password;

    @Size(max = 100)
    private String description;

    @AssertTrue(message = "パスワードは6文字以上20文字以下で入力してください。")
    public boolean isPasswordValid() {

        if (password == null || !password.matches("^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")) {
            return true;
        } else if (password.length() <= 6 || 20 <= password.length()) {
            return false;
        }
        return true;
    }

}
