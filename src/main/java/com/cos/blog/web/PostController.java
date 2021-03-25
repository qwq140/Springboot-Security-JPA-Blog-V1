package com.cos.blog.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetails;
import com.cos.blog.domain.post.Post;
import com.cos.blog.service.PostService;
import com.cos.blog.web.dto.CMRespDto;
import com.cos.blog.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
	
	@GetMapping("/")
	public String findAll(Model model, 
			@PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 10) Pageable pageable,
			@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam(required = false, defaultValue = "") String keyword) {
		
		System.out.println("누구로 로그인 됐을까?");
		//System.out.println(principalDetails.isOAuth());
		//System.out.println(principalDetails.getUser().getUsername());
		
		//Page<Post> posts = postService.전체찾기(pageable);
		Page<Post> posts = postService.검색하기(pageable,keyword);
		model.addAttribute("posts",posts);
		return "post/list"; // 기본적으로 return은 forwarding
	}
	
	
	@GetMapping("/post/saveForm")
	public String saveForm() {
		return "post/saveForm";
	}
	
	@PostMapping("/post")
	public @ResponseBody CMRespDto<?> save(@Valid @RequestBody PostSaveReqDto postSaveReqDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("글쓰기 시작");
		Post post = postSaveReqDto.toEntity(); // 외부에서 받은 것
		post.setUser(principalDetails.getUser());
		
		// 영속화
		Post postEntity = postService.글쓰기(post);
		
		return new CMRespDto<>(1,postEntity);
	}
	
	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id); // 영속화
		model.addAttribute("post", postEntity);
		return "post/detail"; // ViewResolver 
	}
	
	@DeleteMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable int id){
		postService.삭제하기(id);
		return new CMRespDto<>(1,null);
	}
	
	@GetMapping("/post/{id}/updateForm")
	public String updateForm(@PathVariable int id,Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post", postEntity);
		return "post/updateForm";
	}
	
	@PutMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @Valid @RequestBody PostSaveReqDto dto, BindingResult bindingResult) {
		
		postService.수정하기(id, dto);
		return new CMRespDto<>(1,null);
	}
}
