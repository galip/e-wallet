package com.kn.ewallet.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
	private String userName;
	private String password;

}