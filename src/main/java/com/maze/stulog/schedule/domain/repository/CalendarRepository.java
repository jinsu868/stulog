package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Calendar;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalendarRepository {

    private final CalendarJpaRepository calendarJpaRepository;


    public Calendar save(Calendar calendar) {
        return calendarJpaRepository.save(calendar);
    }

    public Optional<Calendar> findById(Long calendarId) {
        return calendarJpaRepository.findById(calendarId);
    }

    public void deleteById(Long calendarId) {
        calendarJpaRepository.deleteById(calendarId);
    }
}
