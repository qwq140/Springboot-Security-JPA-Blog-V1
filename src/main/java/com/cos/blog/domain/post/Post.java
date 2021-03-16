package com.cos.blog.domain.post;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	private String title;

	@Lob // 대용량 데이터
	private String content;
	
	@ColumnDefault("0")
	private int count;
	
	@ManyToOne // EAGER 타입 (페이징 때문에 셀렉트를 2번함, 페이징을 안하면 조인함)
	@JoinColumn(name = "userId")
	private User user;
	
	// 양방향 매핑 (post를 select를 할 때 reply도 가져와야 하므로) - 역방향 매핑
	@JsonIgnoreProperties({"post"}) // reply 안에 있는 post를 getter 때리지 마라.
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) //mapppedBy 변수명(reply안에 있는 변수명) fk를 가지지 않는다. 
	@OrderBy("id desc")
	private List<Reply> reply;
	@CreationTimestamp
	private Timestamp createDate;
}
