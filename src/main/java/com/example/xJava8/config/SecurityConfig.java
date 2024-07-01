package com.example.xJava8.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 5.7.11 たぶん
 *
 *
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(login -> login   // フォーム認証の設定記述開始
                .loginProcessingUrl("/login")   // ユーザー名・パスワードの送信先URL
                .loginPage("/login?auth")            // ログインが必要な場合にユーザーを送信するURLを指定
                .defaultSuccessUrl("/home", true)     // ログイン成功後のリダイレクト先URL
                .failureUrl("/login?error")     // ログイン失敗後のリダイレクト先URL
                .permitAll()                    // ログイン画面は未ログインでもアクセス可能
        ).logout(logout -> logout               // ログアウトの設定記述開始
                .logoutSuccessUrl("/login?logout")  // ログアウト成功後のリダイレクト先URL
                .permitAll()
        ).authorizeHttpRequests(authz -> authz
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()    // 恐らくresourcesフォルダへのアクセスを可能とさせるも
                .antMatchers("/signup")
                    .permitAll()    // /はログイン無しでもアクセス可能
                .antMatchers("/general")
                    .hasRole("GENERAL") // /generalはROLE_GENERALのみアクセス可能
                .antMatchers("/admin")
                    .hasRole("ADMIN")   // /adminはROLE_ADMINのみアクセス可能
                .anyRequest().authenticated()   // 他のURLはログイン後のみアクセス可能
//        ).exceptionHandling(configurer -> configurer
//                .authenticationEntryPoint(customAuthenticationEntryPoint())
        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}
