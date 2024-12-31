package com.maze.stulog.subscription.application;

import static com.maze.stulog.common.error.ExceptionCode.SUBSCRIPTION_NOT_FOUND;

import com.maze.stulog.common.error.BusinessException;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.subscription.domain.Subscription;
import com.maze.stulog.schedule.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void check(
            Long subscriptionId,
            Member member
    ) {
        Subscription subscription = findSubscription(subscriptionId);
        subscription.validateSubscriber(member.getId());

        subscription.toggleCheck();
    }

    private Subscription findSubscription(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new BusinessException(SUBSCRIPTION_NOT_FOUND));
    }
}
