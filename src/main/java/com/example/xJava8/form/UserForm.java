package com.example.xJava8.form;

import com.example.xJava8.validator.NgCheck;
import com.example.xJava8.validator.groups.EditUserValidator;
import com.example.xJava8.validator.groups.SignUpValidator;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserForm {

    private Integer id;

    @NotNull(groups = {SignUpValidator.class, EditUserValidator.class})
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$", groups = {SignUpValidator.class, EditUserValidator.class})
    @Size(max = 20, groups = {SignUpValidator.class, EditUserValidator.class})
    @NgCheck(groups = {SignUpValidator.class, EditUserValidator.class})
    private String name;

    @NotNull(groups = {SignUpValidator.class, EditUserValidator.class})
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$", groups = {SignUpValidator.class, EditUserValidator.class})
    @Size(max = 50, groups = {SignUpValidator.class, EditUserValidator.class})
    private String email;

    @NotNull(groups = {SignUpValidator.class})
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$", groups = {SignUpValidator.class})
    private String password;

    @Size(max = 100, groups = {SignUpValidator.class, EditUserValidator.class})
    @NgCheck(groups = {SignUpValidator.class, EditUserValidator.class})
    private String description;

    @AssertTrue(message = "パスワードは6文字以上20文字以下で入力してください。", groups = {SignUpValidator.class, EditUserValidator.class})
    public boolean isPasswordValid() {

        if (password == null || !password.matches("^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")) {
            return true;
        } else if (password.length() < 6 || 20 < password.length()) {
            return false;
        }
        return true;
    }

}
