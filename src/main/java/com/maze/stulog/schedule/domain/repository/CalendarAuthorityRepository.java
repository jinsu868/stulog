package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.CalendarAuthority;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalendarAuthorityRepository {

    private final CalendarAuthorityJpaRepository calendarAuthorityJpaRepository;

    public void save(CalendarAuthority calendarAuthority) {
        calendarAuthorityJpaRepository.save(calendarAuthority);
    }

    public Optional<CalendarAuthority> findByMemberIdAndCalendarId(Long memberId, Long calendarId) {
        return calendarAuthorityJpaRepository.findByMemberIdAndCalendarId(memberId, calendarId);
    }
}
