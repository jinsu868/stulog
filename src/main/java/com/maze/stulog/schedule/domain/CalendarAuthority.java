package com.maze.stulog.schedule.domain;

import static com.maze.stulog.common.error.ExceptionCode.*;

import com.maze.stulog.common.error.BusinessException;
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
public class CalendarAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long calendarId;

    @Enumerated(value = EnumType.STRING)
    private CalendarRole role;

    private CalendarAuthority(
            Long memberId,
            Long CalendarId,
            CalendarRole role
    ) {
        this.memberId = memberId;
        this.calendarId = CalendarId;
        this.role = role;
    }

    public static CalendarAuthority create(
            Long memberId,
            Long calendarId,
            CalendarRole role
    ) {
        return new CalendarAuthority(
                memberId,
                calendarId,
                role
        );
    }

    public void validateRole(PermissionLevel permissionLevel) {
        if (!role.hasPermission(permissionLevel)) {
            throw new BusinessException(INSUFFICIENT_PERMISSION_LEVEL);
        }
    }
}
