package com.example.xJava8.form;

import com.example.xJava8.validator.groups.CreateTokenValidator;
import com.example.xJava8.validator.groups.UnlockValidator;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UnlockForm {

    @NotNull(groups = {CreateTokenValidator.class, UnlockValidator.class})
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$", groups = {CreateTokenValidator.class, UnlockValidator.class})
    @Size(max = 20, groups = {CreateTokenValidator.class, UnlockValidator.class})
    private String name;

    @NotNull(groups = {UnlockValidator.class})
    @Pattern(regexp = "^[\\s\\S]*[^(\\s|　)]+[\\s\\S]*$", groups = {UnlockValidator.class})
    private String token;

}
