package com.example.xJava8.filter;

import com.example.xJava8.model.LoginUserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * アカウント凍結時の機能制限Filter
 */
@Component
public class AccountRestrictionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        LoginUserDetails loginUser = (LoginUserDetails) securityContext.getAuthentication().getPrincipal();

        if (loginUser.getLoginUser().getNgCount() >= 5) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("./home?restriction");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
