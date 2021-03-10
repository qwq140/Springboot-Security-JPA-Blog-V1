package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC등록
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// IoC등록만 하면 AuthenticationManager가 Bcrypt로 자동 검증해줌.
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user","/post").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") // ROLE_는 강제성이 있음.
			.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin() // x-www-form-urlencoded 로 전송 (Json으로 던지면 못 받음)
			.loginPage("/loginForm")
			.loginProcessingUrl("/login"); // 스프링 시큐리티가 /login 주소가 들어오면 낚아챔
	}
}
