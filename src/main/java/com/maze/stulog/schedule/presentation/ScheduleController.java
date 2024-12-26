package com.maze.stulog.schedule.presentation;

import com.maze.stulog.auth.presentation.annotation.AuthUser;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.schedule.application.ScheduleService;
import com.maze.stulog.schedule.dto.request.PersonalCalendarCreateRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> createPersonalCalendar(
            @AuthUser Member member,
            @RequestBody PersonalCalendarCreateRequest personalCalendarCreateRequest
    ) {
        Long calendarId = scheduleService.saveCalendar(member, personalCalendarCreateRequest);

        return ResponseEntity.created(URI.create("api/v1/calendars/" + calendarId)).build();
    }

}
