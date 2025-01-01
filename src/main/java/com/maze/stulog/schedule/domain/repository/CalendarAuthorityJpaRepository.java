package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.CalendarAuthority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CalendarAuthorityJpaRepository extends JpaRepository<CalendarAuthority, Long> {

    Optional<CalendarAuthority> findByMemberIdAndCalendarId(Long memberId, Long calendarId);

    @Query("""
        DELETE FROM CalendarAuthority ca
        WHERE ca.calendarId = :calendarId
    """)
    @Modifying
    void deleteByCalendarId(Long calendarId);
}
