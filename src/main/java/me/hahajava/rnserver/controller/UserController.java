package me.hahajava.rnserver.controller;

import lombok.AllArgsConstructor;
import me.hahajava.rnserver.model.User;
import me.hahajava.rnserver.persistence.UserRepository;
import me.hahajava.rnserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
		return new ResponseEntity<>(userRepository.findById(userId), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<String> addUserProfile(@RequestBody @Valid User user, BindingResult br) {
		if (br.hasErrors()) {
			return new ResponseEntity<>(br.getAllErrors().get(0).toString(), HttpStatus.BAD_REQUEST);
		}

		String cryptPassword = passwordEncoder.encode(user.getUserPw());
		user.setUserPw(cryptPassword);

		userRepository.save(user);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@PostMapping("/user/login")
	public ResponseEntity<String> getApiKey(@RequestBody User user) {
		String apiKey = userService.selectApiKey(user);
		if (apiKey != null) {
			return new ResponseEntity<>(apiKey, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

}
