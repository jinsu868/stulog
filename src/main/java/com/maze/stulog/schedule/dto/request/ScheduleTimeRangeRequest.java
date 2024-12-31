package com.maze.stulog.schedule.dto.request;

import java.time.LocalDateTime;

public record ScheduleTimeRangeRequest(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
