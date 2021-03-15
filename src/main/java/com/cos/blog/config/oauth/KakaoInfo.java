package com.cos.blog.config.oauth;

import java.util.Map;

public class KakaoInfo extends OAuth2UserInfo{

	public KakaoInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		Map<String, Object> properties = (Map) attributes.get("properties");
		return (String)properties.get("nickname");
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		Map<String, Object> kakaoAcount = (Map) attributes.get("kakao_account");
		return (String)kakaoAcount.get("email");
	}

	@Override
	public String getImageUrI() {
		// TODO Auto-generated method stub
		Map<String, Object> kakaoAcount = (Map) attributes.get("kakao_account");
		Map<String, Object> profile = (Map) kakaoAcount.get("profile");
		return (String)profile.get("profile_image_url");
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return "Kakao_"+attributes.get("id").toString();
	}

	
}
