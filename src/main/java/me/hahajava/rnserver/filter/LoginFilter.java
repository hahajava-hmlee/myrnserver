package me.hahajava.rnserver.filter;

import me.hahajava.rnserver.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Order(1)
@Component
@WebFilter(urlPatterns = "/api/**")
public class LoginFilter implements Filter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String apiKey = httpServletRequest.getHeader("api_key");

        if (apiKey == null) {
            throw new ServletException("error");
        }
        if (isAuthorizedKey(apiKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isAuthorizedKey(String apiKey) {
        return userRepository.findByToken(apiKey) != null;
    }
}
