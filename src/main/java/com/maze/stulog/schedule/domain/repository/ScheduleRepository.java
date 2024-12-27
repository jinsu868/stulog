package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Schedule;
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
}
