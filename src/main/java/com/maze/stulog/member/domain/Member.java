package com.maze.stulog.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 4098, nullable = false)
    private String profile;

    @Column(length = 50, nullable = false)
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public static Member of(
            String socialId,
            String name,
            String profile
    ) {
        return new Member(
                socialId,
                name,
                profile
        );
    }

    private Member(
            String socialId,
            String name,
            String profile
    ) {
        this.socialId = socialId;
        this.name = name;
        this.profile = profile;
        this.role = Role.USER;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
