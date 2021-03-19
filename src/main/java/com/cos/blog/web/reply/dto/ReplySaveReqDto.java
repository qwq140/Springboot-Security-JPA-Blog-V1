package com.cos.blog.web.reply.dto;

import javax.validation.constraints.NotBlank;

import com.cos.blog.domain.reply.Reply;

import lombok.Data;

@Data
public class ReplySaveReqDto {

	@NotBlank(message = "내용을 입력하세요")
	private String content;
	private Integer postId;
	
	public Reply toEntity() {
		return Reply.builder()
				.content(content)
				.build();
	}
}
