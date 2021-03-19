package com.cos.blog.web.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserUpdateReqDto {
	
	private String username;
	@NotBlank(message = "패스워드가 입력되지 않았습니다.")
	private String password;
	private String email;
	
	// toEntity 안만드는 이유는 더티체킹 할 것이기 때문!!

}
