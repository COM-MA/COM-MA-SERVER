package com.example.comma.domain.user.service;
import com.example.comma.domain.user.dto.response.UserInfoResponseDto;
import com.example.comma.domain.user.dto.response.UserTokenResponseDto;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.global.config.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    private final Environment env;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public Long socialLogin(String code, String registrationId) {
        String accessToken = getOauthToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        String socialId = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        String profileImage = userResourceNode.get("picture").asText();
        UserInfoResponseDto user = saveMember(socialId, nickname, email, profileImage);
        Long userId = user.id();
        return userId;

    }

    public String getOauthToken(String authorizationCode, String registrationId) {
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        String accessToken =  accessTokenNode.get("access_token").asText();
        return accessToken;
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
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