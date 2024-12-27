package com.maze.stulog.schedule.domain;

import static com.maze.stulog.schedule.domain.PermissionLevel.CHANGE_PERMISSION;
import static com.maze.stulog.schedule.domain.PermissionLevel.DELETE_SCHEDULE;
import static com.maze.stulog.schedule.domain.PermissionLevel.INSERT_SCHEDULE;
import static com.maze.stulog.schedule.domain.PermissionLevel.UPDATE_CALENDAR;
import static com.maze.stulog.schedule.domain.PermissionLevel.UPDATE_SCHEDULE;

import java.util.EnumSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum CalendarRole {
    ADMIN(EnumSet.of(UPDATE_SCHEDULE, DELETE_SCHEDULE, INSERT_SCHEDULE, UPDATE_CALENDAR, CHANGE_PERMISSION)),
    NONE(Set.of());

    private final Set<PermissionLevel> permissionLevels;

    public boolean hasPermission(PermissionLevel permissionLevel) {
        return permissionLevels.contains(permissionLevel);
    }
}
