package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalendarRepository {

    private final CalendarJpaRepository calendarJpaRepository;


    public Calendar save(Calendar calendar) {
        return calendarJpaRepository.save(calendar);
    }
}
