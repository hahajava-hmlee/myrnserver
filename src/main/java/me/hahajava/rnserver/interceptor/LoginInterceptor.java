package me.hahajava.rnserver.interceptor;

import me.hahajava.rnserver.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("api_key");

        if (apiKey == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return isAuthorizedKey(apiKey);
    }

    private boolean isAuthorizedKey(String apiKey) {
        return userRepository.findByToken(apiKey) != null;
    }
}
