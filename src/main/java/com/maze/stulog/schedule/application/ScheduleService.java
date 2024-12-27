package com.maze.stulog.schedule.application;

import static com.maze.stulog.common.error.ExceptionCode.*;
import static com.maze.stulog.schedule.domain.PermissionLevel.INSERT_SCHEDULE;

import com.maze.stulog.common.error.BusinessException;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.schedule.domain.Calendar;
import com.maze.stulog.schedule.domain.CalendarAuthority;
import com.maze.stulog.schedule.domain.CalendarRole;
import com.maze.stulog.schedule.domain.Schedule;
import com.maze.stulog.schedule.domain.Subscription;
import com.maze.stulog.schedule.domain.repository.CalendarAuthorityRepository;
import com.maze.stulog.schedule.domain.repository.CalendarRepository;
import com.maze.stulog.schedule.domain.repository.ScheduleRepository;
import com.maze.stulog.schedule.dto.request.PersonalCalendarCreateRequest;
import com.maze.stulog.schedule.dto.request.ScheduleCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CalendarRepository calendarRepository;
    private final CalendarAuthorityRepository calendarAuthorityRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * @param member : 로그인 회원 정보
     * @param personalCalendarCreateRequest : 캘린더 생성을 위한 값들.
     * @return : 생성된 캘린더 Id
     */
    @Transactional
    public Long saveCalendar(
            Member member,
            PersonalCalendarCreateRequest personalCalendarCreateRequest
    ) {
        Calendar calendar = Calendar.from(personalCalendarCreateRequest.name());
        Subscription subscription = Subscription.of(calendar, member.getId());
        calendar.addSubscription(subscription);

        Long calendarId = calendarRepository.save(calendar).getId();
        CalendarAuthority calendarAuthority = CalendarAuthority.create(
                member.getId(),
                calendarId,
                CalendarRole.ADMIN
        );
        calendarAuthorityRepository.save(calendarAuthority);

        return calendarId;
    }

    /**
     * @param calendarId : 추가하려는 캘린더 Id
     * @param member : 현재 로그인 유저
     * @param scheduleCreateRequest : 스케줄 추가를 위한 값들.
     * @return : 생성된 스케줄 아이디
     */
    @Transactional
    public Long saveSchedule(
            Long calendarId,
            Member member,
            ScheduleCreateRequest scheduleCreateRequest
    ) {
        Calendar calendar = findCalendar(calendarId);
        CalendarAuthority calendarAuthority = findCalendarAuthority(calendarId, member);
        calendarAuthority.validateRole(INSERT_SCHEDULE);

        Schedule schedule = Schedule.createSchedule(
                calendar,
                scheduleCreateRequest.title(),
                scheduleCreateRequest.content(),
                scheduleCreateRequest.startTime(),
                scheduleCreateRequest.endTime()
        );

        return scheduleRepository.save(schedule).getId();
    }

    private CalendarAuthority findCalendarAuthority(Long calendarId, Member member) {
        return calendarAuthorityRepository.findByMemberIdAndCalendarId(member.getId(), calendarId)
                .orElseThrow(() -> new BusinessException(CALENDAR_AUTHORITY_NOT_FOUND));
    }

    private Calendar findCalendar(Long calendarId) {
        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new BusinessException(CALENDAR_NOT_FOUND));
    }
}