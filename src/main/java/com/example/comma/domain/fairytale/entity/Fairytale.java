package com.example.comma.domain.fairytale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.Year;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "fairytale")
@Entity
public class Fairytale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fairytale_id")
    private Long id;

    private String title;

    private String imgaeUrl;

    private String recommendImageUrl;

    private String channelName;

    private Year year;

    private Time time;

    private String link;

    private String description;

    private Boolean subtitleTag;

    private Boolean signTag;

    @OneToMany(mappedBy = "fairytale")
    private List<UserFairytale> userFairytaleList;

}
