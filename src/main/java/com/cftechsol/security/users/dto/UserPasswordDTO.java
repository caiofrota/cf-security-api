package com.cftechsol.security.users.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDTO implements Serializable {

	private static final long serialVersionUID = -5592924861188534579L;

	private Long id;

	private String password;

}
