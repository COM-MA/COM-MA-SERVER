package com.example.comma.domain.user.service;

import com.example.comma.domain.user.dto.response.UserInfoResponseDto;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    private final Environment env;
    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public Long socialLogin(String code, String registrationId) {
        String accessToken = getOauthToken(code);
        JsonNode userResourceNode = getUserResource(accessToken);
        String socialId = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        String profileImage = userResourceNode.get("picture").asText();
        System.out.println("Social ID: " + socialId);
        System.out.println("Email: " + email);
        System.out.println("Nickname: " + nickname);
        System.out.println("Profile Image: " + profileImage);

        UserInfoResponseDto user = saveMember(socialId, nickname, email, profileImage);
        Long userId = user.id();
        return userId;

    }

    public String getOauthToken(String code) {
        String tokenUri = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> responseBody = responseEntity.getBody();
        if (responseBody != null && responseBody.containsKey("access_token")) {
            return (String) responseBody.get("access_token");
        } else {
            throw new IllegalStateException("Access token not found in response");
        }
    }

    public JsonNode getUserResource(String accessToken) {
        String apiUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to retrieve user resource: " + responseEntity.getStatusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode userResourceNode;
        try {
            userResourceNode = objectMapper.readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse user resource response", e);
        }

        return userResourceNode;
    }

    public UserInfoResponseDto saveMember(String socialId, String name, String email, String profileImage) {
        User existMember = userRepository.findBySocialId(socialId);

        if (existMember == null) {
            User user = User.builder()
                    .socialId(socialId)
                    .name(name)
                    .email(email)
                    .profileImage(profileImage)
                    .build();
            userRepository.save(user);

            return new UserInfoResponseDto(user.getId(), user.getSocialId(), user.getName(), user.getEmail(), user.getProfileImage());
        }

        return new UserInfoResponseDto(existMember.getId(), existMember.getSocialId(), existMember.getName(), existMember.getEmail(), existMember.getProfileImage());
    }
}
