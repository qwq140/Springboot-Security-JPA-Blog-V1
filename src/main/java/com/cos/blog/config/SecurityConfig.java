package com.cos.blog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cos.blog.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

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
			.antMatchers("/user/**","/post/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") // ROLE_는 강제성이 있음.
			.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin() // x-www-form-urlencoded 로 전송 (Json으로 던지면 못 받음)
			.loginPage("/loginForm")
			.loginProcessingUrl("/login")// 스프링 시큐리티가 /login 주소가 들어오면 낚아챔
//			.successHandler(new AuthenticationSuccessHandler() { // 무조건 인증 후 / 를 탐
//				
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//					response.sendRedirect("/"); 
//					
//				}
//			});
			.defaultSuccessUrl("/") // x-www-urlencoded (어느 경로로 갈려고하는데 인증에 막히면 인증 후 그 경로로 감)
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService);
	}
}
