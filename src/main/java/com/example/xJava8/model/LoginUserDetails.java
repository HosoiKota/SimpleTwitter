package com.example.xJava8.model;

import com.example.xJava8.entity.LoginUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUserDetails implements UserDetails {

    private final LoginUser loginUser;
//    private final Collection<? extends GrantedAuthority> authorities;

    public LoginUserDetails(LoginUser loginUser) {
        this.loginUser = loginUser;
//        this.authorities = authorities;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    // ロールのコレクションを返す
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // ハッシュ化済みのパスワードを返す
    @Override
    public String getPassword() {
        return loginUser.getPassword();
    }

    // ログインで利用するユーザー名を返す
    @Override
    public String getUsername() {
        return loginUser.getName();
    }

    // ユーz－が期限切れ出なければtrueを返す
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // ユーザーがロックされていなければtrueを返す
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // ユーザーのパスワードが有効期限切れでなければtrueを変えs
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // ユーザーが有効であればtrueを返す
    @Override
    public boolean isEnabled() {
        return true;
    }
}
