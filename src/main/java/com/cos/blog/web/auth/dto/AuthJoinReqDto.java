package com.cos.blog.web.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.blog.domain.user.User;

import lombok.Data;

// Vaild 나중에 처리하기

@Data
public class AuthJoinReqDto {
	
	@NotBlank(message = "유저네임을 입력하세요")
	@Size(max = 20, message = "입력길이를 초과하셨습니다.")
	private String username;
	
	@NotBlank(message = "비밀번호를 입력하세요")
	private String password;
	private String email;

	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
	}
	
}
