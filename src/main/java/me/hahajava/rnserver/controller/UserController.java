package me.hahajava.rnserver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hahajava.rnserver.model.User;
import me.hahajava.rnserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<String> addUserProfile(@RequestBody @Valid User user, BindingResult br) {

		if (br.hasErrors()) {
			return new ResponseEntity<>(br.getAllErrors().get(0).toString(), HttpStatus.BAD_REQUEST);
		}

		try {
			userService.registerUser(user);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("failed", HttpStatus.NOT_ACCEPTABLE);
		}
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

	@GetMapping("/api/**")
    public String test(){
	    return "test";
    }

}
