package com.cos.blog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer>{
	Page<Post> findByTitleContainingOrContentContaining(Pageable pageable, String title, String content);

}
