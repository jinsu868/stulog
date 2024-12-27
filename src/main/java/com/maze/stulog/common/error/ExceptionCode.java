package com.maze.stulog.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    INVALID_REQUEST(1000, "유효하지 않은 요청입니다."),

    UNABLE_TO_GET_USER_INFO(2001, "소셜 로그인 공급자로부터 유저 정보를 받아올 수 없습니다."),
    UNABLE_TO_GET_ACCESS_TOKEN(2002, "소셜 로그인 공급자로부터 인증 토큰을 받아올 수 없습니다."),

    UNAUTHORIZED_ACCESS(3000, "접근할 수 없는 리소스입니다."),
    INVALID_REFRESH_TOKEN(3001, "유효하지 않은 Refresh Token입니다."),
    FAILED_TO_VALIDATE_TOKEN(3002, "토큰 검증에 실패했습니다."),
    INVALID_ACCESS_TOKEN(3003, "유효하지 않은 Access Token입니다."),

    MEMBER_NOT_FOUND(4000, "사용자가 존재하지 않습니다."),

    /**
     * 5xxx : 스케줄 관련 에러 코드
     */
    CALENDAR_NOT_FOUND(5000, "해당 캘린더를 찾을 수 없습니다."),
    CALENDAR_AUTHORITY_NOT_FOUND(5001, "해당 CalendarAuthority를 찾을 수 없습니다."),
    INSUFFICIENT_PERMISSION_LEVEL(5002, "캘린더 접근 권한이 부족합니다.");

    private final int code;
    private final String message;
}
