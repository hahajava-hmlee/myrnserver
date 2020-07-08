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
	@Column(name = "NO")
	private Long userNo;

	@NotNull
	@Column(name = "USER_ID", nullable = false, unique = true)
	private String id;

	@NotNull
	@Column(name = "USER_PW", nullable = false)
	private String userPw;

	@Column(name = "TOKEN")
	private String token;

}
