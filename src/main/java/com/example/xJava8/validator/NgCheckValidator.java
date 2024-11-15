package com.example.xJava8.validator;

import com.example.xJava8.entity.LoginUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NgCheckValidator implements ConstraintValidator<NgCheck, String> {

    private static final Pattern NG_WORD = Pattern.compile("馬鹿|阿保");

    @Override
    public void initialize(NgCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Matcher matcher = NG_WORD.matcher(value);

        if (matcher.matches()) {
            // ここで誰がNGワードを出したかカウント
            SecurityContext securityContext = SecurityContextHolder.getContext();
            LoginUser loginUser = (LoginUser) securityContext.getAuthentication().getPrincipal();
            return false;
        }

        return !value.matches("NG");
    }
}
