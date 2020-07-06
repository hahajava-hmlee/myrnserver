package me.hahajava.rnserver.service;

import lombok.AllArgsConstructor;
import me.hahajava.rnserver.model.User;
import me.hahajava.rnserver.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String selectApiKey(User requestedUser) {
        User user = userRepository.findById(requestedUser.getId());

        final String rawPw = requestedUser.getUserPw();
        final String encodedPw = user.getUserPw();

        if (passwordEncoder.matches(rawPw, encodedPw)) {
            return user.getToken();
        }
        return null;
    }

    public User registerUser(User user) {
        String rawPw = user.getUserPw();
        String encodedPw = passwordEncoder.encode(rawPw);
        user.setUserPw(encodedPw);
        return userRepository.save(user);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId);
    }
}
