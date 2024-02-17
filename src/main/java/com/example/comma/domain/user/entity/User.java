package com.example.comma.domain.user.entity;

import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.fairytale.entity.UserFairytale;
import com.example.comma.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(nullable = false)
    private Long socialId;

    @OneToMany(mappedBy = "user")
    private List<UserFairytale> userFairytaleList;

    @OneToMany(mappedBy = "user")
    private List<UserCard> userCardList;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

