package com.example.xJava8.model;

import com.example.xJava8.entity.LoginUser;
import lombok.EqualsAndHashCode;
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

    // ユーザ－が期限切れ出なければtrueを返す
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // ユーザーがロックされていなければtrueを返す
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // ユーザーのパスワードが有効期限切れでなければtrueを返す
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // ユーザーが有効であればtrueを返す
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // 引数で渡されたオブジェクトがこのオブジェクト自身であった場合true
            return true;
        }

        if (!(obj instanceof LoginUserDetails)) { // 引数で渡されたオブジェクトが、Userクラスのオブジェクト
            return false;               // では無い場合はfalse。
        }

        LoginUserDetails otherUser = (LoginUserDetails) obj;
        if (this.getLoginUser().getId() == otherUser.getLoginUser().getId()) { // IDの値を比較し、等しければtrue、等しくなければfalse。
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getLoginUser().getId();
    }
}
