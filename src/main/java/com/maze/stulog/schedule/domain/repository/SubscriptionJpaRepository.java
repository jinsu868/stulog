package com.maze.stulog.schedule.domain.repository;

import com.maze.stulog.subscription.domain.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long>{

    @Query("""
        SELECT sb
        FROM Subscription sb 
        JOIN FETCH sb.calendar c      
        WHERE sb.memberId = :memberId
    """)
    List<Subscription> findAllCheckedSubscriptionByMemberId(Long memberId);
}
