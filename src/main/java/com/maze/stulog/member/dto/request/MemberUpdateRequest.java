package com.maze.stulog.member.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record MemberUpdateRequest(
        String name,
        String profile,
        String description
) {
    public static MemberUpdateRequest of(
            String name,
            String profile,
            String description
    ) {
        return new MemberUpdateRequest(
                name,
                profile,
                description
        );
    }
}
