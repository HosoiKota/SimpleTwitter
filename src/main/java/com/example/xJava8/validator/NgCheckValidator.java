package com.example.xJava8.validator;

import com.example.xJava8.model.LoginUserDetails;
import com.example.xJava8.model.NgCheckRequestCounter;
import com.example.xJava8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NgCheckValidator implements ConstraintValidator<NgCheck, String> {

    private static final Pattern NG_WORD = Pattern.compile("馬鹿|阿保");
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private UserService userService;

    @Autowired
    private NgCheckRequestCounter counter;

    @Override
    public void initialize(NgCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Matcher matcher = NG_WORD.matcher(value);

        if (matcher.find()) {
            // ここで誰がNGワードを出したかカウント
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();

            // ログイン認証できているかチェック、できていなければそもそもカウント上げれない
            // 1.nullチェック 2.承認チェック 3.PrincipalがString型かどうか 4.PrincipalがanonymousUserかどうか
            if (authentication != null && authentication.isAuthenticated() &&
                    (authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals(ANONYMOUS_USER))) {
               return false;
            }

            LoginUserDetails loginUser = (LoginUserDetails) securityContext.getAuthentication().getPrincipal();

            counter.incrementCount();
            if (counter.getCount() <= 1) {
                // 同リクエストでNGチェックが1回以下ならカウントup
                userService.updateNgCount(loginUser.getLoginUser().getId());
            }

            return false;
        }

        return true;
    }
}
