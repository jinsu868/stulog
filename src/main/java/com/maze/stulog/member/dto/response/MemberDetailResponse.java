package com.maze.stulog.member.dto.response;

import com.maze.stulog.member.domain.Member;

public record MemberDetailResponse(
        Long id,
        String name,
        String profile,
        String description
) {
    public static MemberDetailResponse from(Member member) {
        return new MemberDetailResponse(
                member.getId(),
                member.getName(),
                member.getProfile(),
                member.getDescription()
        );
    }
}
