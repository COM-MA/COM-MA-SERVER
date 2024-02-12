package com.example.comma.domain.user;

import com.example.comma.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private Long socialId;

    private String profileImg;

    private String nickName;

    @Builder
    public User(Long socialId, String nickName, String profileImg) {
        this.socialId = socialId;
        this.nickName = nickName;
        this.profileImg = profileImg;
    }
}

