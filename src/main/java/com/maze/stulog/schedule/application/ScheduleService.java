package com.maze.stulog.schedule.application;

import com.maze.stulog.member.domain.Member;
import com.maze.stulog.schedule.domain.Calendar;
import com.maze.stulog.schedule.domain.Subscription;
import com.maze.stulog.schedule.domain.repository.CalendarRepository;
import com.maze.stulog.schedule.domain.repository.SubscriptionRepository;
import com.maze.stulog.schedule.dto.request.PersonalCalendarCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CalendarRepository calendarRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Long saveCalendar(
            Member member,
            PersonalCalendarCreateRequest personalCalendarCreateRequest
    ) {
        Calendar calendar = Calendar.from(personalCalendarCreateRequest.name());
        Subscription subscription = Subscription.of(calendar, member.getId());
        calendar.addSubscription(subscription);

        return calendarRepository.save(calendar).getId();
    }
}
