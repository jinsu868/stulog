package com.maze.stulog.schedule.presentation;

import com.maze.stulog.auth.presentation.annotation.AuthUser;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.schedule.application.ScheduleService;
import com.maze.stulog.schedule.dto.request.PersonalCalendarCreateRequest;
import com.maze.stulog.schedule.dto.request.ScheduleCreateRequest;
import com.maze.stulog.schedule.dto.request.ScheduleTimeRangeRequest;
import com.maze.stulog.schedule.dto.request.ScheduleUpdateRequest;
import com.maze.stulog.schedule.dto.response.ScheduleResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/calendars")
    public ResponseEntity<Void> createPersonalCalendar(
            @AuthUser Member member,
            @RequestBody PersonalCalendarCreateRequest personalCalendarCreateRequest
    ) {
        Long calendarId = scheduleService.saveCalendar(member, personalCalendarCreateRequest);

        return ResponseEntity.created(URI.create("api/v1/calendars/" + calendarId)).build();
    }

    @PostMapping("/schedules/{calendarId}")
    public ResponseEntity<Void> createSchedule(
            @PathVariable(name = "calendarId") Long calendarId,
            @AuthUser Member member,
            @RequestBody ScheduleCreateRequest scheduleCreateRequest
    ) {
        Long scheduleId = scheduleService.saveSchedule(calendarId, member, scheduleCreateRequest);

        return ResponseEntity.created(URI.create("api/v1/schedules/" + scheduleId)).build();
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable(name = "scheduleId") Long scheduleId,
            @AuthUser Member member,
            @RequestBody ScheduleUpdateRequest scheduleUpdateRequest
    ) {
        scheduleService.updateSchedule(scheduleId, member, scheduleUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable(name = "scheduleId") Long scheduleId,
            @AuthUser Member member
    ) {
        scheduleService.deleteSchedule(scheduleId, member);

        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @param member : 로그인 유저 정보
     * @param scheduleTimeRangeRequest : 조회할 스케줄 시간 범위
     * @return 해당 유저의 시간 범위에 해당하는 모든 스케줄을 가져온다.
     */
    @GetMapping("/schedules/my")
    public ResponseEntity<List<ScheduleResponse>> findMyAllCheckedSchedules(
            @AuthUser Member member,
            @RequestBody ScheduleTimeRangeRequest scheduleTimeRangeRequest
    ) {
        var response = scheduleService.findMyAllCheckedSchedules(member, scheduleTimeRangeRequest);

        return ResponseEntity.ok(response);
    }
}
