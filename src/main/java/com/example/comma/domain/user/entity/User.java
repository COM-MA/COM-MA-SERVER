package com.example.comma.domain.user.entity;

import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.fairytale.entity.UserFairytale;
import com.example.comma.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String name;

    private String email;

    private String profileImage;

    @Column(nullable = false)
    private String socialId;

    @OneToMany(mappedBy = "user")
    private List<UserFairytale> userFairytaleList;

    @OneToMany(mappedBy = "user")
    private List<UserCard> userCardList;

    @Builder
    public User(String socialId, String name, String email, String profileImage) {
        this.socialId = socialId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

