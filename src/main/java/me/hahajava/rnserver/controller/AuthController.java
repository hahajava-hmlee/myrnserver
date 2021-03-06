package me.hahajava.rnserver.controller;

import lombok.AllArgsConstructor;
import me.hahajava.rnserver.model.UserAccount;
import me.hahajava.rnserver.persistence.UserRepository;
import me.hahajava.rnserver.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> doLogin(@RequestBody UserAccount userAccount) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userAccount.getId(), userAccount.getPw());
        Authentication authentication = authenticationManager.authenticate(auth);

        if (authentication.isAuthenticated() == false) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final String token = JwtUtil.encodeStringToJwt(userAccount.getId());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUserAccount(@RequestBody UserAccount userAccount, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<>(br.getAllErrors().get(0).toString(), HttpStatus.BAD_REQUEST);
        }

        String cryptPassword = passwordEncoder.encode(userAccount.getPw());
        userAccount.setPw(cryptPassword);

        UserAccount account = UserAccount.newInstanceForRegister(userAccount);
        try {
            userRepository.save(account);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
