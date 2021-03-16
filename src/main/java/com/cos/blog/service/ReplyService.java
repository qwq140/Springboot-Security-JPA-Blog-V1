package com.cos.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

	private final ReplyRepository replyRepository;
	
	@Transactional
	public int 삭제하기(int id, int userId) {
		Reply replyEntity = replyRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		if(replyEntity.getUser().getId() == userId) {
			replyRepository.deleteById(id);
			return 1;
		} else {
			return -1;
		}
	}
	
	@Transactional
	public Reply 댓글등록(Reply reply) {
		return replyRepository.save(reply);
	}
	
}
