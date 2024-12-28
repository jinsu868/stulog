package com.maze.stulog.schedule.dto.request;

import java.time.LocalDateTime;

public record ScheduleCreateRequest(
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
