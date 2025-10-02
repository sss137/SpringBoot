package org.shark.boot19.oauth2.service;

import java.util.Map;

import org.shark.boot19.oauth2.entity.CustomOAuth2User;
import org.shark.boot19.oauth2.entity.User;
import org.shark.boot19.oauth2.repository.UserRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    //----- 요청 정보 확인
    String accessToken = userRequest.getAccessToken().getTokenValue();
    System.out.println("AccessToken: " + accessToken);
    ClientRegistration clientRegistration = userRequest.getClientRegistration();
    System.out.println("ClientRegistration: " + clientRegistration);
    //----- 프로필 정보 처리
    OAuth2User oAuth2User = super.loadUser(userRequest);
    String registrationId = clientRegistration.getRegistrationId();
    switch (registrationId) {
    case "kakao": return kakaoUser(oAuth2User);
    case "naver": return naverUser(oAuth2User);
    default: return oAuth2User; 
    }
    
  }
  
  private OAuth2User kakaoUser(OAuth2User oAuth2User) {
    // 카카오가 보낸 모든 응답 정보들
    Map<String, Object> attributes = oAuth2User.getAttributes();
    // 카카오가 보낸 id
    String kakaoId = attributes.get("id").toString();
    // 카카오가 보낸 properties
    @SuppressWarnings("unchecked")
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
    // properties에서 nickname, profile_image 꺼내기
    String nickname = (String) properties.get("nickname");
    String profileImage = (String) properties.get("profile_image");
    // 카카오가 보낸 프로필을 저장하거나 그냥 사용하거나 선택
    User foundUser = userRepository.findById(kakaoId).orElse(null);
    if (foundUser == null) {
      User user = User.CreateUser(kakaoId, nickname, "", profileImage);
      foundUser = userRepository.save(user);
    }
    // foundUser (카카오로부터 받은 프로필 정보)를 OAuth2User 타입으로 변경한 뒤 반환
    // 반환한 OAuth2User는 Authentication 객체에 포함되어 SecurityContext에 저장됩니다.
    return new CustomOAuth2User(attributes, foundUser);
  }
  
  private OAuth2User naverUser(OAuth2User oAuth2User) {
    // 네이버가 보낸 모든 응답 정보들
    Map<String, Object> attributes = oAuth2User.getAttributes();
    // 네이버가 보낸 response
    @SuppressWarnings("unchecked")
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    // response에서 id(항상 제공), name(필수 제공), profile_image(필수 제공), email(선택 제공) 꺼내기
    String naverId = (String) response.get("id");
    String name = (String) response.get("name");    
    String profileImage = (String) response.get("profile_image");
    String email = response.get("email") == null ? "" : (String) response.get("email");
    // 네이버가 보낸 프로필을 저장하거나 그냥 사용하거나 선택
    User foundUser = userRepository.findById(naverId).orElse(null);
    if (foundUser == null) {
      User user = User.CreateUser(naverId, name, email, profileImage);
      foundUser = userRepository.save(user);
    }
    return new CustomOAuth2User(attributes, foundUser);
  }
  
}
