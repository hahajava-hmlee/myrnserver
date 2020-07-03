package me.hahajava.rnserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	private Long userNo;

	@NotNull
	@Column(nullable = false,
			unique = true,
			name = "userId")
	private String id;

	@NotNull
	@Column(nullable = false, name = "USER_PW")
	private String userPw;

	@NotNull
	@Column(name = "TOKEN")
	private String token;

}
