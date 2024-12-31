package com.maze.stulog.schedule.dto.response;

import com.maze.stulog.schedule.domain.Schedule;
import java.time.LocalDateTime;

public record ScheduleResponse(
    Long id,
    Long calendarId,
    String title,
    String content,
    LocalDateTime startTime,
    LocalDateTime endTime
) {
    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getCalendar().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }
}
