# 스프링부트 블로그 프로젝트 V1

## 의존성
- Spring Web
- Lombok
- Spring Dev Tool
- OAuth
- JPA
- Security
- MySQL Driver
```xml
<!-- 추가 라이브러리  -->
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
</dependency>


<!-- JSP 템플릿 엔진 -->
<dependency>
	<groupId>org.apache.tomcat</groupId>
	<artifactId>tomcat-jasper</artifactId>
	<version>9.0.43</version>
</dependency>

<!-- JSTL -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
	<version>1.2</version>
</dependency>
```
## DB 설정

```sql
create user 'pos'@'%' identified by 'pos1234';
GRANT ALL PRIVILEGES ON *.* TO 'pos'@'%';
create database pos;
```

## 시큐리티

```java
@RequiredArgsConstructor
@Configuration // IoC등록
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	// IoC등록만 하면 AuthenticationManager가 Bcrypt로 자동 검증해줌.
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**","/post/**","/reply/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") // ROLE_는 강제성이 있음.
			.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin() // x-www-form-urlencoded 로 전송 (Json으로 던지면 못 받음)
			.loginPage("/loginForm")
			.loginProcessingUrl("/login")// 스프링 시큐리티가 /login 주소가 들어오면 낚아챔
			.defaultSuccessUrl("/") // x-www-urlencoded (어느 경로로 갈려고하는데 인증에 막히면 인증 후 그 경로로 감)
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService);
	}
}
```

## 로그인 화면
![image](https://user-images.githubusercontent.com/74044232/115179317-09258500-a10e-11eb-8da3-4d5cac96e04b.png)

### OAuth 로그인(구글, 페이스북, 네이버, 카카오)
```java
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
		//1번 통합 클래스를 생성
		OAuth2UserInfo oAuth2UserInfo = null;
		System.out.println("뭐로 로그인?" + userRequest.getClientRegistration().getClientName());
		if(userRequest.getClientRegistration().getClientName().equals("Google")) {
			oAuth2UserInfo=new GoogleInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getClientName().equals("Facebook")) {
			oAuth2UserInfo=new FacebookInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getClientName().equals("Naver")) {
			oAuth2UserInfo = new NaverInfo((Map)(oauth2User.getAttributes().get("response")));
		} else if(userRequest.getClientRegistration().getClientName().equals("Kakao")) {
			oAuth2UserInfo = new KakaoInfo(oauth2User.getAttributes());
		}
		
		// 2번 최초 : 회원가입 + 로그인, 최초x : 로그인
		User userEntity = userRepository.findByUsername(oAuth2UserInfo.getUsername());
		
		UUID uuid = UUID.randomUUID();
		String encPassword = new BCryptPasswordEncoder().encode(uuid.toString());
				
		
		if(userEntity == null) { // DB에 없으면 최초 접속(회원가입)
			User user = User.builder()
					.username(oAuth2UserInfo.getUsername())
					.password(encPassword)
					.email(oAuth2UserInfo.getEmail())
					.role(RoleType.USER)
					.build();
			userEntity = userRepository.save(user);
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}else { // 이미 회원가입이 완료됐다는 뜻(원래는 구글 정보가 변경될 수 있기 때문에 update 해야되는데 지금은 안하겠음)
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}
		
	}
```



## 회원가입
![image](https://user-images.githubusercontent.com/74044232/115180015-94ebe100-a10f-11eb-9d46-c3860ce45a45.png)

### BCrypt를 이용하여 비밀번호 암호화
```java
@RequiredArgsConstructor
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
}
```


## 메인페이지
![image](https://user-images.githubusercontent.com/74044232/115180577-e47edc80-a110-11eb-9a5e-10f8f3445d6b.png)

### 기능
- 페이징
- 검색

```java
@GetMapping("/")
public String findAll(Model model, 
		@PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 10) Pageable pageable,
		@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam(required = false, defaultValue = "") String keyword) {
				
	//Page<Post> posts = postService.전체찾기(pageable);
	Page<Post> posts = postService.검색하기(pageable,keyword);
	model.addAttribute("posts",posts);
	return "post/list"; // 기본적으로 return은 forwarding
}
```

```java
public interface PostRepository extends JpaRepository<Post, Integer>{
	Page<Post> findByTitleContainingOrContentContaining(Pageable pageable, String title, String content);

}
```

## 글쓰기
썸머노트 에디터 이용
![image](https://user-images.githubusercontent.com/74044232/115181078-13498280-a112-11eb-8663-0a8fb718cb82.png)

## 게시글 상세보기
![image](https://user-images.githubusercontent.com/74044232/115181377-c4501d00-a112-11eb-8fe7-f8b736381148.png)

### 기능
- 댓글작성, 삭제
- 자기가 쓴 글일 경우 수정, 삭제 가능

#### 비동기 통신으로 댓글 구현하기 위해 Ajax 사용
- 댓글 등록
```javascript
$("#btn-reply-save").on("click",(e)=>{

	  let data = {
			content:$("#reply-content").val(),
			postId:e.currentTarget.value
	  };
			
	  $.ajax({
			type:"POST",
			url:"/reply",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
	  }).done((res)=>{
			console.log(res);
		    if(res.statusCode===1){
				history.go(0);
			} else {
				alert("댓글 등록에 실패하셨습니다.");
			}
	  });

 });
```

- 댓글 삭제
```javascript
function deleteReply(id){
	$.ajax({
		type:"DELETE",
		url:"/reply/"+id,
		dataType:"json"
	}).done((res)=>{
		console.log(res);
		if(res.statusCode===1){
			$("#reply-"+id).remove();
		} else {
			alert("수정에 실패하였습니다.");
		}
	});
}
```

#### 게시글 수정, 삭제의 경우 FORM태그에서 PUT, DELETE를 지원하지 않기 때문에 Ajax통신 사용
- 게시글 삭제
```javascript
$("#btn-delete").on("click",(e)=>{
	let id = e.currentTarget.value;

	$.ajax({
		type:"DELETE",
		url:"/post/"+id,
		dataType:"json"
	}).done(res=>{
		if(res.statusCode === 1){
			alert("삭제에 성공하였습니다.");
			history.back();
		} else {
			alert("삭제에 실패했습니다.");
		}
	});
});
```

- 게시글 수정
```javascript
$("#btn-update").on("click",(e)=>{
    	let id = $("#id").val();

    	let data = {
    		title:$("#title").val(),
    		content:$("#content").val()
    	};

    			
    	$.ajax({
    		type:"PUT",
    		url:"/post/"+id,
    		data:JSON.stringify(data),
    		contentType:"application/json; charset=utf-8",
    		dataType:"json"
    	}).done((res)=>{
    		console.log(res);
    		if(res.statusCode===1){
    		alert("수정에 성공하였습니다.");
    		history.go(-1);
    		} else {
    			alert("수정에 실패하였습니다.");
    		}
    	});

});
```

## 회원정보수정
![image](https://user-images.githubusercontent.com/74044232/115182786-8a344a80-a115-11eb-8dbf-0fa526fb2b3f.png)
패스워드와 email 수정가능


## 디자인
![녹화_2021_03_25_23_55_31_809](https://user-images.githubusercontent.com/74044232/112493889-e41b4c00-8dc5-11eb-8369-43e988bc5761.gif)
