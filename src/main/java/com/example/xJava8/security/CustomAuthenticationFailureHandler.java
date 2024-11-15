package com.example.xJava8.security;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 認証エラーに対するハンドラークラス
 *
 * 主なAuthenticationExceptionのサブクラス
 * BadCredentialsException	ユーザが存在しない、パスワードが間違ってるなど
 * LockedException	UserDetails.isAccountNonLocked() が false を返した
 * DisabledException	UserDetails.isEnabled() が false を返した
 * AccountExpiredException	UserDetails.isAccountNonExpired() が false を返した
 * CredentialsExpiredException	UserDetails.isCredentialsNonExpired() が false を返した
 * SessionAuthenticationException	セッション数が上限を超えた場合など（詳細後日）
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof LockedException) {
            // アカウントロック判定

            // アカウントロック解消への画面に誘導したい。
            response.sendRedirect("./login?error");
        } else {
            response.sendRedirect("./login?error");
        }
    }
}
