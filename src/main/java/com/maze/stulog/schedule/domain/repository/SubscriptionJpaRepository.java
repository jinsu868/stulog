package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.schedule.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {
}
