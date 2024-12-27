package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    public Schedule save(Schedule schedule) {
        return scheduleJpaRepository.save(schedule);
    }
}