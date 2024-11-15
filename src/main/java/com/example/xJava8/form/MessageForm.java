package com.example.xJava8.form;

import com.example.xJava8.validator.NgCheck;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MessageForm {

    private Integer id;

    @NotNull
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$")
    @Size(max = 140)
    @NgCheck
    private String message;

    private String userId;
}
