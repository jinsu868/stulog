package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Calendar;
import com.maze.stulog.schedule.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

    @Query("""
        SELECT sc
        FROM Schedule sc
        WHERE sc.calendar in :calendars
        AND sc.startTime >= :startTime
        AND sc.endTime <= :endTime
    """)
    List<Schedule> findAllSchedulesByCalendarsAndBetweenTime(
            List<Calendar> calendars,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
