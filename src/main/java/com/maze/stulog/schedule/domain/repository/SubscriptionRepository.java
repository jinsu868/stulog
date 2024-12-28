package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Calendar;
import com.maze.stulog.schedule.domain.Subscription;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepository {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public Subscription save(Subscription subscription) {
        return subscriptionJpaRepository.save(subscription);
    }

    public Optional<Subscription> findById(Long subscriptionId) {
        return subscriptionJpaRepository.findById(subscriptionId);
    }
}
