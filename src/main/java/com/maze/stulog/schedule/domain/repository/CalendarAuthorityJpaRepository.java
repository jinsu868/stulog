package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.CalendarAuthority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarAuthorityJpaRepository extends JpaRepository<CalendarAuthority, Long> {

    Optional<CalendarAuthority> findByMemberIdAndCalendarId(Long memberId, Long calendarId);
}
