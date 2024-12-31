package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Calendar;
import com.maze.stulog.schedule.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    public Schedule save(Schedule schedule) {
        return scheduleJpaRepository.save(schedule);
    }

    public Optional<Schedule> findById(Long scheduleId) {
        return scheduleJpaRepository.findById(scheduleId);
    }

    public void delete(Schedule schedule) {
        scheduleJpaRepository.delete(schedule);
    }

    public List<Schedule> findAllByCalendarsBetweenTime(
            List<Calendar> calendars,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        return scheduleJpaRepository.findAllSchedulesByCalendarsAndBetweenTime(calendars, startTime, endTime);
    }
}
